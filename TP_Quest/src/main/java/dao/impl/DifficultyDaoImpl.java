package dao.impl;

import dao.DifficultyDao;
import dao.domain.Difficulty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class DifficultyDaoImpl implements DifficultyDao {

    private Connection connection = null;

    public DifficultyDaoImpl(Connection connection) throws SQLException {
        this.connection = connection;
        createTable();
    }

    @Override
    public Difficulty get(String name, String password) {
        try {

            PreparedStatement stm = connection.prepareStatement("select * from difficulty where name=? and password=?");
            stm.setString(1, name);
            stm.setString(2, password);
            ResultSet resultSet = stm.executeQuery();

            List<Difficulty> resultDataList = new ArrayList<>();
            while (resultSet.next()) {
                resultDataList.add(new Difficulty(resultSet.getLong(1), resultSet.getInt(2)));
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
    public int getRecord(long id) {
        try {

            PreparedStatement stm = connection.prepareStatement("select score from difficulty where id =?");
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
    public Difficulty get(long id) {
        try {

            PreparedStatement stm = connection.prepareStatement("select * from difficulty where id=?");
            stm.setLong(1, id);
            ResultSet resultSet = stm.executeQuery();

            List<Difficulty> resultDataList = new ArrayList<>();
            while (resultSet.next()) {
                resultDataList.add(new Difficulty(resultSet.getLong(1), resultSet.getInt(2)));
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
    public List<Difficulty> get(Predicate<Difficulty> predicate) throws SQLException {

        PreparedStatement stm = connection.prepareStatement("select * from difficulty");
        ResultSet resultSet = stm.executeQuery();

        List<Difficulty> resultDataList = new ArrayList<>();
        while (resultSet.next()) {
            resultDataList.add(new Difficulty(resultSet.getLong(1), resultSet.getInt(2)));
        }
        stm.close();
        resultSet.close();
        return resultDataList;
    }

    @Override
    public void save(Difficulty difficultyData) throws SQLException {
        PreparedStatement stm = connection.prepareStatement("insert into difficulty (score) values (?)");
        stm.setLong(1, difficultyData.getScore());
        stm.executeUpdate();

        stm.close();
    }

    @Override
    public void update(Difficulty difficultyData, String[] params) throws SQLException {
        PreparedStatement stm = connection.prepareStatement("update difficulty set score=? where id=?");
        stm.setInt(1, difficultyData.getScore());
        stm.setLong(2, difficultyData.getId());
        stm.executeUpdate();

        stm.close();
    }

    @Override
    public void delete(Difficulty difficultyData) throws SQLException {
        PreparedStatement stm = connection.prepareStatement("delete from difficulty where id=?");
        stm.setLong(1, difficultyData.getId());
        stm.executeUpdate();

        stm.close();
    }

    @Override
    public void createTable() throws SQLException {
        PreparedStatement stm = connection.prepareStatement("CREATE TABLE IF NOT EXISTS difficulty(" +
                "  id SERIAL PRIMARY KEY," +
                "  score INTEGER NOT NULL," +
                "  UNIQUE(score))");
        stm.executeUpdate();

        stm.close();
    }

    @Override
    public void dropTable() throws SQLException {
        PreparedStatement stm = connection.prepareStatement("drop table difficulty");
        stm.executeUpdate();

        stm.close();
    }

    @Override
    public void clearTable() throws SQLException {
        PreparedStatement stm = connection.prepareStatement("delete from difficulty");
        stm.executeUpdate();

        stm.close();
    }
}
