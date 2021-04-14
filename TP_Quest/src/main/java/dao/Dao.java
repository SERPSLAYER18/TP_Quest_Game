package dao;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Predicate;

public interface Dao<T> {

    T get(long id);

    List<T> get(Predicate<T> predicate) throws SQLException;

}

