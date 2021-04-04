package dao.impl;

import service.queryExecutor.SQLExecutor;
import dao.domain.Question;
import dao.QuestionDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.Predicate;

public class QuestionDaoImpl implements QuestionDao {
    private SQLExecutor executor = null;

    public QuestionDaoImpl(Connection connection) throws SQLException {
        executor = new SQLExecutor(connection);
        createTable();
    }

    public long getTopicIndex(String topic){
        try{
            return executor.sqlQuery("select id from topic where name = '" + topic +
                "'", resultSet -> {
            ArrayList<Long> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(resultSet.getLong(1));
            }
            return list;
        }).get(0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;

    }
    public long getDifficultyIndex(String difficulty) {
        try {
            return executor.sqlQuery("select id from difficulty where score = " + difficulty, resultSet -> {
                ArrayList<Long> list = new ArrayList<>();
                while (resultSet.next()) {
                    list.add(resultSet.getLong(1));
                }
                return list;
            }).get(0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public ArrayList<Question>getQuestions(long id_topic, long id_difficulty){
        try {
            return executor.sqlQuery("SELECT q.id,q.text,q.answer,t.name,d.score " +
                            "FROM ((questions q " +
                            "INNER JOIN topic t ON t.id = "+id_topic+" and q.id_topic = "+id_topic+") " +
                            "INNER JOIN difficulty d ON d.id = "+id_difficulty+" and q.id_difficulty = "+id_difficulty+")", resultSet -> {
                        ArrayList<Question> list = new ArrayList<>();
                        while (resultSet.next()) {
                            list.add(new Question(resultSet));
                        }
                        return list;
                    }
            );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public ArrayList<Question>getQuestions(String topic, String difficulty){

        long id_difficulty,id_topic;

        id_topic = getTopicIndex(topic);
        id_difficulty =  getDifficultyIndex(difficulty);

        return getQuestions(id_topic,id_difficulty);
    }

    public Question getRandomQuestion(String topic, String difficulty){

        Random rnd = new Random(System.currentTimeMillis());

        ArrayList<Question> questions= getQuestions(topic,difficulty);
        return questions.get(Math.abs(rnd.nextInt()%questions.size()));
    }

    public String getCorrectAnswer(Question newQuestion){
        try {
            return executor.sqlQuery("select answer from questions where id = " + newQuestion.getId(), resultSet -> {
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
    public Question get(long id) {
        try {
            return executor.sqlQuery("SELECT q.id,q.text,q.answer,t.name,d.score " +
                            "FROM ((questions q " +
                            "INNER JOIN topic t ON t.id = q.id_topic) " +
                            "INNER JOIN difficulty d ON d.id = and q.id_difficulty) " +
                            "WHERE q.id =" +id, resultSet -> {
                        ArrayList<Question> list = new ArrayList<>();
                        while (resultSet.next()) {
                            list.add(new Question(resultSet));
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
    public ArrayList<Question> get(Predicate<Question> predicate) throws SQLException {

        return executor.sqlQuery("SELECT q.id,q.text,q.answer,t.name,d.score " +
                "FROM ((questions q " +
                "INNER JOIN topic t ON t.id = q.id_topic) " +
                "INNER JOIN difficulty d ON d.id = and q.id_difficulty) ", resultSet -> {
                    ArrayList<Question> list = new ArrayList<>();
                    while (resultSet.next()) {
                        Question User = new Question(resultSet);
                        if (predicate.test(User))
                            list.add(User);
                    }
                    return list;
                }
        );

    }

    @Override
    public void save(Question questionData) throws SQLException {

        long id_difficulty,id_topic;
        id_topic =  getTopicIndex(questionData.getTopic());
        id_difficulty =  getDifficultyIndex(questionData.getDifficulty());

        executor.sqlUpdate(
                String.format("insert into questions (text,answer,id_topic,id_difficulty) values ('%s','%s',%d,%d)",
                questionData.getText(),
                questionData.getAnswer(),
                id_topic,
                id_difficulty));

    }

    @Override
    public void update(Question questionData, String[] params) throws SQLException {

        long id_difficulty,id_topic;
        id_topic = getTopicIndex(questionData.getTopic());
        id_difficulty =  getDifficultyIndex(questionData.getDifficulty());

        executor.sqlUpdate(String.format( "update questions set id_topic=%d,id_difficulty=%d,text='%s',answer='%s' where id = %d",
                id_topic,
                id_difficulty,
                questionData.getText(),
                questionData.getAnswer(),
                questionData.getId()));
    }

    @Override
    public void delete(Question questionData) throws SQLException {
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
