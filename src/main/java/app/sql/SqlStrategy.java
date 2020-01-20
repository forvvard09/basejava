package main.java.app.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlStrategy<T> {
    T execute(PreparedStatement ps) throws SQLException;
}
