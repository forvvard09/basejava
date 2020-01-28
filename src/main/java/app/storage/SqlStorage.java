package main.java.app.storage;

import main.java.app.exception.NotExistStorageException;
import main.java.app.model.ContactType;
import main.java.app.model.Resume;
import main.java.app.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
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
        //!!! sqlHelper.<Void>
        sqlHelper.<Void>execute("INSERT INTO resume (uuid, full_name) values (?, ?)", ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.executeUpdate();
            return null;
        });
        for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
            sqlHelper.<Void>execute("INSERT INTO contact (type, value, resume_uuid) values (?, ?, ?)", ps -> {
                ps.setString(1, resume.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, entry.getValue());
                ps.executeUpdate();
                return null;
            });
        }
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
            do {
                ContactType typeContact = ContactType.valueOf(rs.getString("type"));
                resume.setContact(typeContact, rs.getString("value"));
            } while (rs.next());
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
        sqlHelper.<Void>execute("UPDATE Resume Set full_name = ? WHERE resume.uuid = ?", ps -> {
            ps.setString(1, newResume.getFullName());
            ps.setString(2, newResume.getUuid());
            if (ps.executeUpdate() != 1) {
                throw new NotExistStorageException(newResume.getUuid());
            }
            return null;
        });
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
            /*int countResume = 0;
            if (rs.next()) {
                countResume = rs.getInt(1);
            }
            return countResume;*/
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("SqlStorage. Get sorted list resume.");
        return sqlHelper.execute("SELECT * FROM resume r " +
                "" +
                "ORDER BY full_name, uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> listResume = new ArrayList<>();
            while (rs.next()) {
                listResume.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return listResume;
        });
    }


}
