package main.java.app.storage;

import main.java.app.exception.NotExistStorageException;
import main.java.app.model.Resume;
import main.java.app.sql.ConnectionFactory;
import main.java.app.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    private final ConnectionFactory connectionFactory;
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
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        sqlHelper = new SqlHelper(connectionFactory);
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.execute("INSERT INTO resume (uuid, full_name) values (?, ?)", ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.executeUpdate();
            return null;
        });
        LOG.info("Save sql.");
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("SELECT * FROM resume r WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            LOG.info("Get sql. Uuid: " + uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume r WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() != 1) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
        LOG.info("Delete sql. Uuid: " + uuid);
    }

    @Override
    public void update(Resume newResume) {
        sqlHelper.execute("UPDATE Resume Set full_name = ? WHERE resume.uuid = ?", ps -> {
            ps.setString(1, newResume.getFullName());
            ps.setString(2, newResume.getUuid());
            if (ps.executeUpdate() != 1) {
                throw new NotExistStorageException(newResume.getUuid());
            }
            return null;
        });
        LOG.info("Update sql. Uuid: " + newResume.getUuid());
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
        LOG.info("Clear sql.");
    }

    @Override
    public int getSize() {
        return sqlHelper.execute("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            int countResume = 0;
            if (rs.next()) {
                countResume = rs.getInt(1);
            }
            LOG.info("Get size sql.");
            return countResume;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("SELECT * FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
            }
            LOG.info("Get all sorted.");
            return doSortCopyAll(list);
        });
    }

    private List<Resume> doSortCopyAll(List<Resume> listResume) {
        Collections.sort(listResume);
        return new ArrayList<>(listResume);
    }
}
