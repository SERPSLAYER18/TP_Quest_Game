package JDBC.DAO;

import DataSets.TopicData;
import JDBC.QueryExecutor.SQLExecutor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.Predicate;

public class TopicDAO implements DAO<TopicData> {
    private SQLExecutor executor = null;

    public TopicDAO(Connection connection) throws SQLException {
        executor = new SQLExecutor( connection);
        createTable();
    }

    @Override
    public TopicData get(long id) {
        try {
            return executor.sqlQuery("select * from topic where id = " + id, resultSet -> {
                        ArrayList<TopicData> list = new ArrayList<>();
                        while (resultSet.next()) {
                            list.add(new TopicData(resultSet));
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
    public ArrayList<TopicData> get(Predicate<TopicData> predicate) throws SQLException {

        return executor.sqlQuery("select * from topic", resultSet -> {
                    ArrayList<TopicData> list = new ArrayList<>();
                    while (resultSet.next()) {
                        TopicData topicData = new TopicData(resultSet);
                        if (predicate.test(topicData))
                            list.add(topicData);
                    }
                    return list;
                }
        );

    }

    @Override
    public void save(TopicData topicData) throws SQLException {
        executor.sqlUpdate(String.format("insert into topic (name) values ('%s')",
                topicData.getName()));

    }

    @Override
    public void update(TopicData topicData, String[] params) throws SQLException {
        executor.sqlUpdate(String.format( "update topic set name = %s where id = %d",
                topicData.getName(),
                topicData.getId()));
    }

    @Override
    public void delete(TopicData topicData) throws SQLException {
        executor.sqlUpdate(String.format( "delete from topic where id = %d", topicData.getId()));

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
