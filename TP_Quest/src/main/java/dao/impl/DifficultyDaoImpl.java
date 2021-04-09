package dao.impl;

import service.queryExecutor.SQLExecutor;
import dao.DifficultyDao;
import dao.domain.Difficulty;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class DifficultyDaoImpl implements DifficultyDao {

    private SQLExecutor executor = null;

    public DifficultyDaoImpl(Connection connection) throws SQLException {
        executor = new SQLExecutor(connection);
        createTable();
    }

    @Override
    public Difficulty get(String name, String password) {
        try {
            return executor.sqlQuery("select * from difficulty where name = '" + name +
                            "' and password = '" + password + "'", resultSet -> {
                        List<Difficulty> resultDataList = new ArrayList<>();
                        while (resultSet.next()) {
                            resultDataList.add(new Difficulty(resultSet.getLong(1),resultSet.getInt(2)));
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
    public int getRecord(long id) {
        try {
            return executor.sqlQuery("select score from difficulty where id = " + id, resultSet -> {
                        List<Integer> resultDataList = new ArrayList<>();
                        while (resultSet.next()) {
                            resultDataList.add(resultSet.getInt(1));
                        }
                        return resultDataList;
                    }
            ).get(0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    @Override
    public Difficulty get(long id) {
        try {
            return executor.sqlQuery("select * from difficulty where id = " + id, resultSet -> {
                        List<Difficulty> resultDataList = new ArrayList<>();
                        while (resultSet.next()) {
                            resultDataList.add(new Difficulty(resultSet.getLong(1),
                                    resultSet.getInt(2)));
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
    public List<Difficulty> get(Predicate<Difficulty> predicate) throws SQLException {

        return executor.sqlQuery("select * from difficulty", resultSet -> {
                    List<Difficulty> resultDataList = new ArrayList<>();
                    while (resultSet.next()) {
                        Difficulty difficultyData = new Difficulty(resultSet.getLong(1),
                                resultSet.getInt(2));
                        if (predicate.test(difficultyData))
                            resultDataList.add(difficultyData);
                    }
                    return resultDataList;
                }
        );

    }

    @Override
    public void save(Difficulty difficultyData) throws SQLException {
        executor.sqlUpdate(String.format("insert into difficulty (score) values (%d)",
                difficultyData.getScore()));

    }

    @Override
    public void update(Difficulty difficultyData, String[] params) throws SQLException {
        executor.sqlUpdate(String.format("update difficulty set score = %d where id = %d",
                difficultyData.getScore(),
                difficultyData.getId()));
    }

    @Override
    public void delete(Difficulty difficultyData) throws SQLException {
        executor.sqlUpdate(String.format("delete from difficulty where id = %d", difficultyData.getId()));

    }

    @Override
    public void createTable() throws SQLException {
        executor.sqlUpdate("CREATE TABLE IF NOT EXISTS difficulty(" +
                "  id SERIAL PRIMARY KEY," +
                "  score INTEGER NOT NULL," +
                "  UNIQUE(score))");
    }

    @Override
    public void dropTable() throws SQLException {
        executor.sqlUpdate("drop table difficulty");
    }

    @Override
    public void clearTable() throws SQLException {
        executor.sqlUpdate("delete from difficulty");
    }
}
