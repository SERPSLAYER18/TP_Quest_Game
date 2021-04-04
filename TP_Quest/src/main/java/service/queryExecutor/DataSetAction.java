package service.queryExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface DataSetAction<T> {
    List<T> handle(ResultSet resultSet) throws SQLException;
}
