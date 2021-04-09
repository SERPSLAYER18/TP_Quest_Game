package dao.impl;

import dao.QuestionDao;
import dao.domain.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class QuestionDaoImpl implements QuestionDao {


    private Connection connection = null;

    public QuestionDaoImpl(Connection connection) throws SQLException {
        this.connection = connection;
        createTable();
    }

    public long getTopicIndex(String topic) {
        try {

            PreparedStatement stm = connection.prepareStatement("select id from topic where name=?");
            stm.setString(1, topic);
            ResultSet resultSet = stm.executeQuery();

            List<Long> resultDataList = new ArrayList<>();
            while (resultSet.next()) {
                resultDataList.add(resultSet.getLong(1));
            }
            stm.close();
            resultSet.close();
            return resultDataList.get(0);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 1;

    }

    public long getDifficultyIndex(String difficulty) {
        try {

            PreparedStatement stm = connection.prepareStatement("select id from difficulty where score=?");
            stm.setLong(1, Integer.parseInt(difficulty));
            ResultSet resultSet = stm.executeQuery();

            List<Long> resultDataList = new ArrayList<>();
            while (resultSet.next()) {
                resultDataList.add(resultSet.getLong(1));
            }
            stm.close();
            resultSet.close();
            return resultDataList.get(0);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }


    public List<Question> getQuestions(long topicId, long difficultyId) {
        try {

            PreparedStatement stm = connection.prepareStatement(
                    "SELECT q.id,q.text,q.answer,t.name,d.score FROM ((questions q " +
                            "INNER JOIN topic t ON t.id=? and q.id_topic=?) " +
                            "INNER JOIN difficulty d ON d.id=? and q.id_difficulty=?)");

            stm.setLong(1, topicId);
            stm.setLong(2, topicId);
            stm.setLong(3, difficultyId);
            stm.setLong(4, difficultyId);
            ResultSet resultSet = stm.executeQuery();

            List<Question> resultDataList = new ArrayList<>();
            while (resultSet.next()) {
                resultDataList.add(new Question(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)));
            }
            stm.close();
            resultSet.close();
            return resultDataList;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<Question> getQuestions(String topic, String difficulty) {

        long difficultyId, topicId;

        topicId = getTopicIndex(topic);
        difficultyId = getDifficultyIndex(difficulty);

        return getQuestions(topicId, difficultyId);
    }

    //SQL рандомный вопрос запрос -- ORDER BY RANDOM() limit 1
    public Question getRandomQuestion(String topic, String difficulty) {

        long difficultyId, topicId;

        topicId = getTopicIndex(topic);
        difficultyId = getDifficultyIndex(difficulty);

        try {


            PreparedStatement stm = connection.prepareStatement("SELECT q.id,q.text,q.answer,t.name,d.score " +
                    "FROM ((questions q " +
                    "INNER JOIN topic t ON t.id=?) " +
                    "INNER JOIN difficulty d ON d.id=?) " +
                    "ORDER BY RANDOM() " +
                    "LIMIT 1");

            stm.setLong(1, topicId);
            stm.setLong(2, difficultyId);
            ResultSet resultSet = stm.executeQuery();

            List<Question> resultDataList = new ArrayList<>();
            while (resultSet.next()) {
                resultDataList.add(new Question(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)));
            }
            stm.close();
            resultSet.close();
            return resultDataList.get(0);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public String getCorrectAnswer(Question newQuestion) {

        return getCorrectAnswer(newQuestion.getId());

    }

    public String getCorrectAnswer(long id) {
        try {

            PreparedStatement stm = connection.prepareStatement("select answer from questions where id=?");
            stm.setLong(1, id);
            ResultSet resultSet = stm.executeQuery();

            List<String> resultDataList = new ArrayList<>();
            while (resultSet.next()) {
                resultDataList.add(resultSet.getString(1));
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
    public Question get(long id) {
        try {

            PreparedStatement stm = connection.prepareStatement("SELECT q.id,q.text,q.answer,t.name,d.score " +
                    "FROM ((questions q " +
                    "INNER JOIN topic t ON t.id = q.id_topic) " +
                    "INNER JOIN difficulty d ON d.id = and q.id_difficulty) " +
                    "WHERE q.id=?");
            stm.setLong(1, id);
            ResultSet resultSet = stm.executeQuery();

            List<Question> resultDataList = new ArrayList<>();
            while (resultSet.next()) {
                resultDataList.add(new Question(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)));
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
    public List<Question> get(Predicate<Question> predicate) throws SQLException {

        PreparedStatement stm = connection.prepareStatement("SELECT q.id,q.text,q.answer,t.name,d.score " +
                "FROM ((questions q " +
                "INNER JOIN topic t ON t.id = q.id_topic) " +
                "INNER JOIN difficulty d ON d.id = and q.id_difficulty) ");
        ResultSet resultSet = stm.executeQuery();

        List<Question> resultDataList = new ArrayList<>();
        while (resultSet.next()) {
            resultDataList.add(new Question(resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)));
        }
        stm.close();
        resultSet.close();
        return resultDataList;

    }

    @Override
    public void save(Question questionData) throws SQLException {

        long difficultyId, topicId;
        topicId = getTopicIndex(questionData.getTopic());
        difficultyId = getDifficultyIndex(questionData.getDifficulty());

        PreparedStatement stm = connection.prepareStatement("insert into questions (text,answer,id_topic,id_difficulty) values (?,?,?,?)");
        stm.setString(1, questionData.getText());
        stm.setString(2, questionData.getAnswer());
        stm.setLong(3, topicId);
        stm.setLong(4, difficultyId);
        stm.executeUpdate();

        stm.close();

    }

    @Override
    public void update(Question questionData, String[] params) throws SQLException {

        long difficultyId, topicId;
        topicId = getTopicIndex(questionData.getTopic());
        difficultyId = getDifficultyIndex(questionData.getDifficulty());

        PreparedStatement stm = connection.prepareStatement("update questions set id_topic=?,id_difficulty=?,text=?,answer=? where id = ?");
        stm.setLong(1, topicId);
        stm.setLong(2, difficultyId);
        stm.setString(3, questionData.getText());
        stm.setString(4, questionData.getAnswer());
        stm.setLong(5, questionData.getId());
        stm.executeUpdate();

        stm.close();

    }

    @Override
    public void delete(Question questionData) throws SQLException {

        PreparedStatement stm = connection.prepareStatement("delete from questions where id=?");
        stm.setLong(1, questionData.getId());
        stm.executeUpdate();

        stm.close();
    }

    @Override
    public void createTable() throws SQLException {

        PreparedStatement stm = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS questions(" +
                        " id SERIAL PRIMARY KEY," +
                        " text VARCHAR(150) NOT NULL," +
                        " answer VARCHAR(50) NOT NULL," +
                        " id_topic INTEGER REFERENCES topic(id)," +
                        " id_difficulty INTEGER REFERENCES difficulty(id)," +
                        " UNIQUE(text))");
        stm.executeUpdate();

        stm.close();
    }

    @Override
    public void dropTable() throws SQLException {

        PreparedStatement stm = connection.prepareStatement("DROP TABLE questions ");
        stm.executeUpdate();
    }

    @Override
    public void clearTable() throws SQLException {

        PreparedStatement stm = connection.prepareStatement("TRUNCATE TABLE questions ");
        stm.executeUpdate();
    }
}
