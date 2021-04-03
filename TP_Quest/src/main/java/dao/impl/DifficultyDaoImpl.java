package dao.impl;

import JDBC.QueryExecutor.SQLExecutor;
import dao.DifficultyDao;
import dao.domain.Difficulty;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
                        ArrayList<Difficulty> list = new ArrayList<>();
                        while (resultSet.next()) {
                            list.add(new Difficulty(resultSet));
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
    public int getRecord(long id) {
        try {
            return executor.sqlQuery("select score from difficulty where id = " + id, resultSet -> {
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
    public Difficulty get(long id) {
        try {
            return executor.sqlQuery("select * from difficulty where id = " + id, resultSet -> {
                        ArrayList<Difficulty> list = new ArrayList<>();
                        while (resultSet.next()) {
                            list.add(new Difficulty(resultSet));
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
    public ArrayList<Difficulty> get(Predicate<Difficulty> predicate) throws SQLException {

        return executor.sqlQuery("select * from difficulty", resultSet -> {
                    ArrayList<Difficulty> list = new ArrayList<>();
                    while (resultSet.next()) {
                        Difficulty difficultyData = new Difficulty(resultSet);
                        if (predicate.test(difficultyData))
                            list.add(difficultyData);
                    }
                    return list;
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
