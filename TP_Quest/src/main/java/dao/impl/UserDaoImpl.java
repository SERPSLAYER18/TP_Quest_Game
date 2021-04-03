package dao.impl;

import JDBC.QueryExecutor.SQLExecutor;
import dao.UserDao;
import dao.domain.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.Predicate;

public class UserDaoImpl implements UserDao {
    private SQLExecutor executor = null;

    public UserDaoImpl(Connection connection) throws SQLException {
        executor = new SQLExecutor(connection);
        createTable();
    }

    public User get(String name, String password) {
        try {
            return executor.sqlQuery("select * from game_user where name = '" + name +
                            "' and password = '" + password + "'", resultSet -> {
                        ArrayList<User> list = new ArrayList<>();
                        while (resultSet.next()) {
                            list.add(new User(resultSet));
                        }
                        return list;
                    }
            ).get(0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public int getRecord(long id) {
        try {
            return executor.sqlQuery("select score from game_user where id = " + id, resultSet -> {
                        ArrayList<Integer> list = new ArrayList<>();
                        while (resultSet.next()) {
                            list.add(resultSet.getInt(1));
                        }
                        return list;
                    }
            ).get(0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }


    @Override
    public User get(long id) {
        try {
            return executor.sqlQuery("select * from game_user where id = " + id, resultSet -> {
                        ArrayList<User> list = new ArrayList<>();
                        while (resultSet.next()) {
                            list.add(new User(resultSet));
                        }
                        return list;
                    }
            ).get(0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<User> get(Predicate<User> predicate) throws SQLException {

        return executor.sqlQuery("select * from game_user", resultSet -> {
                    ArrayList<User> list = new ArrayList<>();
                    while (resultSet.next()) {
                        User userData = new User(resultSet);
                        if (predicate.test(userData))
                            list.add(userData);
                    }
                    return list;
                }
        );

    }

    @Override
    public void save(User userData) throws SQLException {
        executor.sqlUpdate(String.format("insert into game_user (name,password,score) values ('%s','%s',%d)",
                userData.getName(),
                userData.getPassword(),
                userData.getScore()));

    }

    @Override
    public void update(User userData, String[] params) throws SQLException {
        executor.sqlUpdate(String.format("update game_user set name = %s, password = %s, score = %d where id = %d",
                userData.getName(),
                userData.getPassword(),
                userData.getScore(),
                userData.getId()));
    }

    @Override
    public void delete(User userData) throws SQLException {
        executor.sqlUpdate(String.format("delete from game_user where id = %d", userData.getId()));

    }

    @Override
    public void createTable() throws SQLException {
        executor.sqlUpdate("CREATE TABLE IF NOT EXISTS game_user (" +
                "  id SERIAL PRIMARY KEY," +
                "  name VARCHAR(50) NOT NULL," +
                "  password VARCHAR(10) NOT NULL," +
                "  score INTEGER DEFAULT 0," +
                "  UNIQUE(name,password))");
    }

    @Override
    public void dropTable() throws SQLException {
        executor.sqlUpdate("drop table game_user");
    }

    @Override
    public void clearTable() throws SQLException {
        executor.sqlUpdate("delete from game_user");
    }
}
