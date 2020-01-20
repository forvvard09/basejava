package main.java.app.sql;

import main.java.app.exception.ExistStorageException;
import main.java.app.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T execute(String sqlQuery, SqlStrategy<T> runnerExecite) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            return runnerExecite.execute(ps);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageException("This uuid already in use.");
            } else {
                throw new StorageException(e);
            }
        }
    }

    public void execute(String sqlQuery) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
