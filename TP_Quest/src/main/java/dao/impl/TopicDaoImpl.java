package dao.impl;

import service.queryExecutor.SQLExecutor;
import dao.TopicDao;
import dao.domain.Topic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Predicate;

public class TopicDaoImpl implements TopicDao {
    private SQLExecutor executor = null;

    public TopicDaoImpl(Connection connection) throws SQLException {
        executor = new SQLExecutor(connection);
        createTable();
    }

    @Override
    public Topic get(long id) {
        try {
            return executor.sqlQuery("select * from topic where id = " + id, resultSet -> {
                        List<Topic> resultDataList = new ArrayList<>();
                        while (resultSet.next()) {
                            resultDataList.add(new Topic(resultSet.getLong(1),
                                    resultSet.getString(2)));
                        }
                        return resultDataList;
                    }
            ).get(0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Topic> get(Predicate<Topic> predicate) throws SQLException {

        return executor.sqlQuery("select * from topic", resultSet -> {
                    List<Topic> resultDataList = new ArrayList<>();
                    while (resultSet.next()) {
                        Topic topicData = new Topic(resultSet.getLong(1),
                                resultSet.getString(2));
                        if (predicate.test(topicData))
                            resultDataList.add(topicData);
                    }
                    return resultDataList;
                }
        );

    }

    @Override
    public void save(Topic topicData) throws SQLException {
        executor.sqlUpdate(String.format("insert into topic (name) values ('%s')",
                topicData.getName()));

    }

    @Override
    public void update(Topic topicData, String[] params) throws SQLException {
        executor.sqlUpdate(String.format("update topic set name = %s where id = %d",
                topicData.getName(),
                topicData.getId()));
    }

    @Override
    public void delete(Topic topicData) throws SQLException {
        executor.sqlUpdate(String.format("delete from topic where id = %d", topicData.getId()));

    }

    @Override
    public void createTable() throws SQLException {
        executor.sqlUpdate("CREATE TABLE IF NOT EXISTS topic(" +
                " id SERIAL PRIMARY KEY," +
                " name VARCHAR(50) NOT NULL," +
                " UNIQUE(name))");
    }

    @Override
    public void dropTable() throws SQLException {
        executor.sqlUpdate("drop table topic");
    }

    @Override
    public void clearTable() throws SQLException {
        executor.sqlUpdate("delete from topic");
    }
}
