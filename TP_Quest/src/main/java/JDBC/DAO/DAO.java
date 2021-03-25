package JDBC.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.Predicate;

public interface DAO<T> {

    T get(long id);

    ArrayList<T> get(Predicate<T> predicate) throws SQLException;

    void save(T t) throws SQLException;

    void update(T t, String[] params) throws SQLException;

    void delete(T t) throws SQLException;

    void createTable() throws SQLException;

    void dropTable() throws SQLException;

    void clearTable() throws SQLException;


}

