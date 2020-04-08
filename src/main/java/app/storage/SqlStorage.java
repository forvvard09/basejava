package main.java.app.storage;

import main.java.app.exception.NotExistStorageException;
import main.java.app.exception.StorageException;
import main.java.app.model.*;
import main.java.app.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    private SqlHelper sqlHelper;

    //Java 7
    /*public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = new ConnectionFactory() {
            @Override
            public Connection getConnection() throws SQLException {
                return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            }
        };
    }*/

    //Java 8 Lambda
    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void save(Resume resume) {
        LOG.info("SqlStorage. Save new resume.");
        sqlHelper.transactionExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) values (?, ?)")) {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, resume.getFullName());
                        ps.executeUpdate();
                    }
            addContacts(conn, resume);
            addSections(conn, resume);
                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("SqlStorage. Get resume. Uuid: " + uuid);
        // плохое место - "" для fullName, но это обязательное поле
        Resume resume = new Resume(uuid, "");
        sqlHelper.transactionExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r " +
                    "WHERE r.uuid =?"
            )) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume.setFullName(rs.getString("full_name"));
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * from contact c " +
                    "WHERE c.resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ContactType typeContact = ContactType.valueOf(rs.getString("type"));
                    resume.setContact(typeContact, rs.getString("value"));
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * from section s " +
                    "WHERE s.resume_uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();

                if (rs.getString("type") != null) {
                    while (rs.next()) {
                        SectionType typeSection = SectionType.valueOf(rs.getString("type"));
                        resume.setSection(typeSection, writeSections(typeSection, rs.getString("value"), resume.getUuid()));
                    }
                }
            }
            return null;
        });
        return resume;
    }

    @Override
    public void delete(String uuid) {
        LOG.info("SqlStorage. Delete resume. Uuid: " + uuid);
        sqlHelper.<Void>execute("DELETE FROM resume r WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() != 1) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public void update(Resume newResume) {
        LOG.info("SqlStorage. Update resume. Uuid: " + newResume.getUuid());
        sqlHelper.transactionExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE Resume Set full_name = ? " +
                    "WHERE resume.uuid = ?")) {
                        ps.setString(1, newResume.getFullName());
                        ps.setString(2, newResume.getUuid());
                        if (ps.executeUpdate() != 1) {
                            throw new NotExistStorageException(newResume.getUuid());
                        }
            }
            deleteContacts(conn, newResume.getUuid());
            addContacts(conn, newResume);
            deleteSections(conn, newResume.getUuid());
            addSections(conn, newResume);
                    return null;

                }
        );
    }

    @Override
    public void clear() {
        LOG.info("SqlStorage. Clear storage.");
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public int getSize() {
        LOG.info("SqlStorage. Get size storage.");
        return sqlHelper.execute("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    //первый способ, через запрос с join, через 1 запрос
    /*@Override
    public List<Resume> getAllSorted() {
        LOG.info("SqlStorage. Get sorted list resume.");
        return sqlHelper.execute("SELECT * FROM resume r " +
                        "LEFT JOIN contact c on r.uuid = c.resume_uuid " +
                        "ORDER BY r.full_name, r.uuid",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    return convertResultQueryToSortedListResumes(rs);
                });
    }

    private List<Resume> convertResultQueryToSortedListResumes(ResultSet rs) throws SQLException {
        Map<String, Resume> resultMap = new LinkedHashMap<>();
        while (rs.next()) {
            Resume resume = new Resume(rs.getString("uuid"), rs.getString("full_name"));
            //также можно использовать PutIfAbsent(), но тогда придется повторно искать по мапе для записи контактов
            Resume resumeInMap = resultMap.computeIfAbsent(rs.getString("uuid"), value -> resume);
            if (rs.getString("type") != null) {
                resumeInMap.setContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
            }
        }
        return new ArrayList<>(resultMap.values());
    }*/

    //второй способ через 3 запроса
    @Override
    public List<Resume> getAllSorted() {
        LOG.info("SqlStorage. Get sorted list resume");
        Map<String, Resume> mapResumes = new LinkedHashMap<>();
        sqlHelper.transactionExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r ORDER BY r.full_name, r.uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    mapResumes.put(rs.getString("uuid"), new Resume(rs.getString("uuid"), rs.getString("full_name")));
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * from contact c")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    mapResumes.get(rs.getString("resume_uuid")).setContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
                }

            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * from section s")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    SectionType typeSection = SectionType.valueOf(rs.getString("type"));
                    mapResumes.get(rs.getString("resume_uuid")).setSection(typeSection, writeSections(typeSection, rs.getString("value"), rs.getString("resume_uuid")));
                }

            }
            return null;
        });
        return new ArrayList<>(mapResumes.values());
    }

    private void addContacts(Connection conn, Resume newResume) throws SQLException {
        if (newResume.getContacts().size() > 0) {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (type, value, resume_uuid) values (?, ?, ?)")) {
                for (Map.Entry<ContactType, String> entry : newResume.getContacts().entrySet()) {
                    ps.setString(1, entry.getKey().name());
                    ps.setString(2, entry.getValue());
                    ps.setString(3, newResume.getUuid());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
        }
    }


    private AbstractSection writeSections(SectionType sectionType, String section, String uuidResume) {
        AbstractSection resultSection = null;
        switch (sectionType.name()) {
            case "PERSONAL":
            case "OBJECTIVE":
                resultSection = new TextSection(section);
                break;
            case "ACHIEVEMENT":
            case "QUALIFICATIONS":
                resultSection = new ListSection(section.split(System.lineSeparator()));
                break;
            default:
                break;
        }
        if (resultSection == null) {
            throw new StorageException("Error for get sections: " + sectionType.getTitle(), uuidResume);
        }
        return resultSection;
    }


    private void addSections(Connection conn, Resume newResume) throws SQLException {
        if (newResume.getSections().size() > 0) {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (type, value, resume_uuid) values (?, ?, ?)")) {
                for (Map.Entry<SectionType, AbstractSection> entry : newResume.getSections().entrySet()) {
                    String nameSection = entry.getKey().name();
                    ps.setString(1, nameSection);
                    switch (nameSection) {
                        case "PERSONAL":
                        case "OBJECTIVE":
                            ps.setString(2, ((TextSection) entry.getValue()).getDescription());
                            break;
                        case "ACHIEVEMENT":
                        case "QUALIFICATIONS":
                            ps.setString(2, String.join(System.lineSeparator(), ((ListSection) entry.getValue()).getItems()));
                            break;
                        default:
                            break;
                    }
                    ps.setString(3, newResume.getUuid());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
        }
    }

    private void deleteContacts(Connection conn, String uuidDeleteResume) throws SQLException {
        LOG.info("SqlStorage. Delete contacts.");
        deleteDataResume(conn, "DELETE FROM contact c WHERE c.resume_uuid = ?", uuidDeleteResume);
    }

    private void deleteSections(Connection conn, String uuidDeleteResume) throws SQLException {
        LOG.info("SqlStorage. Delete sections.");
        deleteDataResume(conn, "DELETE FROM section s WHERE s.resume_uuid = ?", uuidDeleteResume);
    }

    private void deleteDataResume(Connection conn, String sqlRequest, String uuidResume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sqlRequest)) {
            ps.setString(1, uuidResume);
            ps.execute();
        }
    }
}