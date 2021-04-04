package service.queryExecutor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SQLExecutor {
    private Connection connection = null;
    Statement statement = null;

    public SQLExecutor(Connection connection) throws SQLException {
        this.connection = connection;
    }

    public void sqlUpdate(String sql) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        statement.close();
    }

    public <T> List<T> sqlQuery(String sql, DataSetAction<T> action) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeQuery(sql);

        ResultSet result = statement.getResultSet();
        List<T> values = action.handle(result);

        statement.close();
        result.close();
        return values;
    }

}
