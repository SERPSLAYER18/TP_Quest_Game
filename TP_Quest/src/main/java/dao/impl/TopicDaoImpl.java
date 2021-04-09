package dao.impl;

import dao.TopicDao;
import dao.domain.Topic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class TopicDaoImpl implements TopicDao {

    private Connection connection = null;

    public TopicDaoImpl(Connection connection) throws SQLException {
        this.connection = connection;
        createTable();
    }

    @Override
    public Topic get(long id) {
        try {

            PreparedStatement stm = connection.prepareStatement("select * from topic where id=?");
            stm.setLong(1, id);
            ResultSet resultSet = stm.executeQuery();

            List<Topic> resultDataList = new ArrayList<>();
            while (resultSet.next()) {
                resultDataList.add(new Topic(
                        resultSet.getLong(1),
                        resultSet.getString(2)));
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
    public List<Topic> get(Predicate<Topic> predicate) throws SQLException {

        PreparedStatement stm = connection.prepareStatement("select * from topic");
        ResultSet resultSet = stm.executeQuery();

        List<Topic> resultDataList = new ArrayList<>();
        while (resultSet.next()) {
            Topic topicData = new Topic(resultSet.getLong(1),
                    resultSet.getString(2));
            if (predicate.test(topicData))
                resultDataList.add(topicData);
        }
        stm.close();
        resultSet.close();
        return resultDataList;

    }

    @Override
    public void save(Topic topicData) throws SQLException {

        PreparedStatement stm = connection.prepareStatement("insert into topic (name) values (?)");
        stm.setString(1, topicData.getName());
        stm.executeUpdate();

        stm.close();
    }

    @Override
    public void update(Topic topicData, String[] params) throws SQLException {

        PreparedStatement stm = connection.prepareStatement("update topic set name=? where id=?");
        stm.setString(1, topicData.getName());
        stm.setLong(2, topicData.getId());
        stm.executeUpdate();

        stm.close();

    }

    @Override
    public void delete(Topic topicData) throws SQLException {
        PreparedStatement stm = connection.prepareStatement("delete from topic where id=?");
        stm.setLong(1, topicData.getId());
        stm.executeUpdate();

        stm.close();
    }

    @Override
    public void createTable() throws SQLException {
        PreparedStatement stm = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS topic(" +
                        " id SERIAL PRIMARY KEY," +
                        " name VARCHAR(50) NOT NULL," +
                        " UNIQUE(name))");
        stm.executeUpdate();

        stm.close();
    }

    @Override
    public void dropTable() throws SQLException {
        PreparedStatement stm = connection.prepareStatement("drop table topic");
        stm.executeUpdate();

        stm.close();
    }

    @Override
    public void clearTable() throws SQLException {
        PreparedStatement stm = connection.prepareStatement("delete from topic");
        stm.executeUpdate();

        stm.close();
    }
}
