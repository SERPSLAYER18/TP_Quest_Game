package dao.impl;

import dao.UserDao;
import dao.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class UserDaoImpl implements UserDao {

    private Connection connection = null;

    public UserDaoImpl(Connection connection) throws SQLException {
        this.connection = connection;
        createTable();
    }

    public User get(String name, String password) {
        try {

            PreparedStatement stm = connection.prepareStatement("select * from game_user where name=? and password=?");
            stm.setString(1, name);
            stm.setString(2, password);
            ResultSet resultSet = stm.executeQuery();

            List<User> resultDataList = new ArrayList<>();
            while (resultSet.next()) {
                resultDataList.add(new User(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3).toCharArray(),
                        resultSet.getInt(4)));
            }
            stm.close();
            resultSet.close();
            return resultDataList.get(0);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public int getRecord(long id) {
        try {

            PreparedStatement stm = connection.prepareStatement("select score from game_user where id=?");
            stm.setLong(1, id);
            ResultSet resultSet = stm.executeQuery();

            List<Integer> resultDataList = new ArrayList<>();
            while (resultSet.next()) {
                resultDataList.add(resultSet.getInt(1));
            }
            stm.close();
            resultSet.close();
            return resultDataList.get(0);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }


    @Override
    public User get(long id) {
        try {

            PreparedStatement stm = connection.prepareStatement("select * from game_user where id=?");
            stm.setLong(1, id);
            ResultSet resultSet = stm.executeQuery();

            List<User> resultDataList = new ArrayList<>();
            while (resultSet.next()) {
                resultDataList.add(new User(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3).toCharArray(),
                        resultSet.getInt(4)));
            }
            stm.close();
            resultSet.close();
            return resultDataList.get(0);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> get(Predicate<User> predicate) throws SQLException {

        PreparedStatement stm = connection.prepareStatement("select * from game_user");
        ResultSet resultSet = stm.executeQuery();

        List<User> resultDataList = new ArrayList<>();
        while (resultSet.next()) {
            User User = new User(resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3).toCharArray(),
                    resultSet.getInt(4));
            if (predicate.test(User))
                resultDataList.add(User);
        }

        stm.close();
        resultSet.close();
        return resultDataList;
    }

    @Override
    public void save(User User) throws SQLException {

        PreparedStatement stm = connection.prepareStatement("insert into game_user (name,password,score) values (?,?,?)");
        stm.setString(1, User.getName());
        stm.setString(2, User.getPassword().toString());
        stm.setInt(3, User.getScore());
        stm.executeUpdate();

        stm.close();
    }

    @Override
    public void update(User User, String[] params) throws SQLException {

        PreparedStatement stm = connection.prepareStatement("update game_user set name=?, password=?, score=? where id=?");
        stm.setString(1, User.getName());
        stm.setString(2, User.getPassword().toString());
        stm.setInt(3, User.getScore());
        stm.setLong(4, User.getId());
        stm.executeUpdate();

        stm.close();
    }

    @Override
    public void delete(User User) throws SQLException {
        PreparedStatement stm = connection.prepareStatement("delete from game_user where id=?");
        stm.setLong(1, User.getId());
        stm.executeUpdate();

        stm.close();
    }

    @Override
    public void createTable() throws SQLException {
        PreparedStatement stm = connection.prepareStatement("CREATE TABLE IF NOT EXISTS game_user (" +
                "  id SERIAL PRIMARY KEY," +
                "  name VARCHAR(50) NOT NULL," +
                "  password VARCHAR(10) NOT NULL," +
                "  score INTEGER DEFAULT 0," +
                "  UNIQUE(name,password))");
        stm.executeUpdate();

        stm.close();
    }

    @Override
    public void dropTable() throws SQLException {
        PreparedStatement stm = connection.prepareStatement("drop table game_user");
        stm.executeUpdate();

        stm.close();
    }

    @Override
    public void clearTable() throws SQLException {
        PreparedStatement stm = connection.prepareStatement("delete from game_user");
        stm.executeUpdate();

        stm.close();
    }
}
