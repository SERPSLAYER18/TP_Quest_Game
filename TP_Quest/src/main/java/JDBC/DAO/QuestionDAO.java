package JDBC.DAO;

import DataSets.QuestionData;
import JDBC.QueryExecutor.SQLExecutor;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.Predicate;

public class QuestionDAO implements DAO<QuestionData> {
    private SQLExecutor executor = null;

    public QuestionDAO(Connection connection) throws SQLException {
        executor = new SQLExecutor(connection);
        createTable();
    }

    public ArrayList<QuestionData>getQuestions(long id_topic, long id_difficulty){
        try {
            return executor.sqlQuery("select * from questions where id_topic = " + id_topic +
                    " and id_difficulty = " + id_difficulty, resultSet -> {
                        ArrayList<QuestionData> list = new ArrayList<>();
                        while (resultSet.next()) {
                            list.add(new QuestionData(resultSet));
                        }
                        return list;
                    }
            );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public String getCorrectAnswer(long id){
        try {
            return executor.sqlQuery("select answer from questions where id = " + id, resultSet -> {
                        ArrayList<String> list = new ArrayList<>();
                        while (resultSet.next()) {
                            list.add(resultSet.getString(1));
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
    public QuestionData get(long id) {
        try {
            return executor.sqlQuery("select * from questions where id = " + id, resultSet -> {
                        ArrayList<QuestionData> list = new ArrayList<>();
                        while (resultSet.next()) {
                            list.add(new QuestionData(resultSet));
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
    public ArrayList<QuestionData> get(Predicate<QuestionData> predicate) throws SQLException {

        return executor.sqlQuery("select * from questions", resultSet -> {
                    ArrayList<QuestionData> list = new ArrayList<>();
                    while (resultSet.next()) {
                        QuestionData userData = new QuestionData(resultSet);
                        if (predicate.test(userData))
                            list.add(userData);
                    }
                    return list;
                }
        );

    }

    @Override
    public void save(QuestionData questionData) throws SQLException {
        executor.sqlUpdate(
                String.format("insert into question (id_topic,id_difficulty,text,answer) values (%d,%d,'%s','%s')",
                questionData.getId_topic(),
                questionData.getId_difficulty(),
                questionData.getText(),
                questionData.getAnswer()));

    }

    @Override
    public void update(QuestionData questionData, String[] params) throws SQLException {
        executor.sqlUpdate(String.format( "update questions set id_topic=%d,id_difficulty=%d,text='%s',answer='%s' where id = %d",
                questionData.getId_topic(),
                questionData.getId_difficulty(),
                questionData.getText(),
                questionData.getAnswer(),
                questionData.getId()));
    }

    @Override
    public void delete(QuestionData questionData) throws SQLException {
        executor.sqlUpdate(String.format( "delete from questions where id = %d", questionData.getId()));

    }

    @Override
    public void createTable() throws SQLException {
        executor.sqlUpdate("CREATE TABLE IF NOT EXISTS questions(" +
                " id SERIAL PRIMARY KEY," +
                " text VARCHAR(150) NOT NULL," +
                " answer VARCHAR(50) NOT NULL," +
                " id_topic INTEGER REFERENCES topic(id)," +
                " id_difficulty INTEGER REFERENCES difficulty(id)," +
                " UNIQUE(text))");
    }

    @Override
    public void dropTable() throws SQLException {
        executor.sqlUpdate("DROP TABLE questions ");
    }

    @Override
    public void clearTable() throws SQLException {
        executor.sqlUpdate("TRUNCATE TABLE questions ");
    }
}
