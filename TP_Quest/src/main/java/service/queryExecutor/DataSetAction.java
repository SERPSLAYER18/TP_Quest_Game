package service.queryExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface DataSetAction<T> {
    ArrayList<T> handle(ResultSet resultSet) throws SQLException;
}
