package main.java.app.storage;

import main.java.app.exception.NotExistStorageException;
import main.java.app.model.ContactType;
import main.java.app.model.Resume;
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
                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("SqlStorage. Get resume. Uuid: " + uuid);
        return sqlHelper.execute("SELECT * FROM resume r " +
                "LEFT JOIN contact c on r.uuid = c.resume_uuid" +
                " WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, rs.getString("full_name"));
            if (rs.getString("type") != null) {
                do {
                    ContactType typeContact = ContactType.valueOf(rs.getString("type"));
                    resume.setContact(typeContact, rs.getString("value"));
                } while (rs.next());
            }
            return resume;
        });
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
                    try (PreparedStatement ps = conn.prepareStatement("UPDATE Resume Set full_name = ? WHERE resume.uuid = ?")) {
                        ps.setString(1, newResume.getFullName());
                        ps.setString(2, newResume.getUuid());
                        if (ps.executeUpdate() != 1) {
                            throw new NotExistStorageException(newResume.getUuid());
                        }
                    }
                    deleteContacts(newResume.getUuid());
                    addContacts(conn, newResume);
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

    @Override
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

    private void deleteContacts(String uuidDeleteResume) {
        LOG.info("SqlStorage. Delete contacts.");
        sqlHelper.<Void>execute("DELETE FROM contact c WHERE c.resume_uuid = ?", ps -> {
            ps.setString(1, uuidDeleteResume);
            ps.execute();
            return null;
        });
    }
}