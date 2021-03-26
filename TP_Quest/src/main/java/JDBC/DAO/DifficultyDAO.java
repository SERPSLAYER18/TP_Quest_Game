package JDBC.DAO;

import DataSets.DifficultyData;
import JDBC.QueryExecutor.SQLExecutor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.Predicate;

public class DifficultyDAO implements DAO<DifficultyData> {
    private SQLExecutor executor = null;

    public DifficultyDAO(Connection connection) throws SQLException {
        executor = new SQLExecutor( connection);
        createTable();
    }

    /**
     *
     * Special SQL queries
     */
    public DifficultyData get(String name,String password) {
        try {
            return executor.sqlQuery("select * from difficulty where name = '" + name +
                    "' and password = '" + password + "'", resultSet -> {
                        ArrayList<DifficultyData> list = new ArrayList<>();
                        while (resultSet.next()) {
                            list.add(new DifficultyData(resultSet));
                        }
                        return list;
                    }
            ).get(0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public int getRecord(long id){
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
    public DifficultyData get(long id) {
        try {
            return executor.sqlQuery("select * from difficulty where id = " + id, resultSet -> {
                        ArrayList<DifficultyData> list = new ArrayList<>();
                        while (resultSet.next()) {
                            list.add(new DifficultyData(resultSet));
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
    public ArrayList<DifficultyData> get(Predicate<DifficultyData> predicate) throws SQLException {

        return executor.sqlQuery("select * from difficulty", resultSet -> {
                    ArrayList<DifficultyData> list = new ArrayList<>();
                    while (resultSet.next()) {
                        DifficultyData difficultyData = new DifficultyData(resultSet);
                        if (predicate.test(difficultyData))
                            list.add(difficultyData);
                    }
                    return list;
                }
        );

    }

    @Override
    public void save(DifficultyData difficultyData) throws SQLException {
        executor.sqlUpdate(String.format("insert into game_user (score) values (%d)",
                difficultyData.getScore()));

    }

    @Override
    public void update(DifficultyData difficultyData, String[] params) throws SQLException {
        executor.sqlUpdate(String.format( "update difficulty set score = %d where id = %d",
                difficultyData.getScore(),
                difficultyData.getId()));
    }

    @Override
    public void delete(DifficultyData difficultyData) throws SQLException {
        executor.sqlUpdate(String.format( "delete from difficulty where id = %d", difficultyData.getId()));

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
