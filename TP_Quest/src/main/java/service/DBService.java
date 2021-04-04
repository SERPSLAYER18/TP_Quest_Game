package service;

import dao.domain.*;
import dao.impl.*;
import dto.*;
import mapper.*;
import server.PropertiesImporter;

import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class DBService {


    private static Flyway flyway;
    public static DBService instance;

    static {
        try {
            instance = new DBService();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static String JDBC_DRIVER;
    public static String DB_URL;
    public static String USER;
    public static String PASS;


    private Connection connection = null;
    private UserDaoImpl userDAO = null;
    private TopicDaoImpl topicDAO = null;
    private DifficultyDaoImpl difficultyDAO = null;
    private QuestionDaoImpl questionDAO = null;


    public DBService() throws SQLException {

        PropertiesImporter pi = new PropertiesImporter();
        JDBC_DRIVER = pi.getProperty("JDBC_DRIVER");
        DB_URL = pi.getProperty("DB_URL");
        USER = pi.getProperty("USER");
        PASS = pi.getProperty("PASS");

        setupConnection();

        userDAO = new UserDaoImpl(connection);
        topicDAO = new TopicDaoImpl(connection);
        difficultyDAO = new DifficultyDaoImpl(connection);
        questionDAO = new QuestionDaoImpl(connection);
    }


    public List<UserDto> getUsers(Predicate<User> predicate) throws SQLException {
        List<UserDto> users = new ArrayList<>();
        List<User> usersDao = userDAO.get(predicate);
        for (var user : usersDao) {
            users.add(UserMapper.userToDto(user));
        }
        return users;
    }

    public UserDto getUser(String name, String password) throws SQLException {
        return UserMapper.userToDto(userDAO.get(name, password));
    }

    public int getUserRecord(UserDto user) {

        return userDAO.getRecord(user.getId());
    }


    public void updateUserRecord(UserDto user, int newRecord) throws SQLException {

        try {
            connection.setAutoCommit(false);
            User userDao = userDAO.get(user.getName(), user.getPassword());
            userDao.setScore(newRecord);
            userDAO.update(userDao, new String[]{});
        } catch (Exception e) {
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }


    public List<QuestionDto> getQuestions(long id_topic, long id_difficulty) {
        List<QuestionDto> questions = new ArrayList<>();
        List<Question> questionsDao = questionDAO.getQuestions(id_topic, id_difficulty);
        for (var user : questionsDao) {
            questions.add(QuestionMapper.questionToDto(user));
        }
        return questions;
    }

    public String getCorrectAnswer(long id) {
        return questionDAO.getCorrectAnswer(id);
    }

    public QuestionDto getRandomQuestion(String topic, String difficulty) {
        return QuestionMapper.questionToDto(questionDAO.getRandomQuestion(topic, difficulty));
    }


    public void saveUser(UserDto user) throws SQLException {
        try {
            connection.setAutoCommit(false);
            userDAO.createTable();
            userDAO.save(new User(user.getId(), user.getName(), user.getPassword(), user.getScore()));
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }

    }

    public void saveQuestion(QuestionDto questionData) throws SQLException {
        try {
            connection.setAutoCommit(false);
            questionDAO.createTable();
            questionDAO.save(new Question(
                    questionData.getId(), questionData.getText(),
                    questionData.getAnswer(), questionData.getTopic(),
                    questionData.getDifficulty()));
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }

    }


    public void clearQuestions() {
        try {
            questionDAO.clearTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void cleanUpUsers() throws SQLException {
        try {
            connection.setAutoCommit(false);
            userDAO.dropTable();
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }


    public List<TopicDto> getTopics(Predicate<Topic> predicate) throws SQLException {
        List<TopicDto> topics = new ArrayList<>();
        List<Topic> topicsDao = topicDAO.get(predicate);
        for (var user : topicsDao) {
            topics.add(TopicMapper.topicToDto(user));
        }
        return topics;
    }

    public List<DifficultyDto> getDifficulties(Predicate<Difficulty> predicate) throws SQLException {
        List<DifficultyDto> difficulties = new ArrayList<>();
        List<Difficulty> difficultiesDao = difficultyDAO.get(predicate);
        for (var user : difficultiesDao) {
            difficulties.add(DifficultyMapper.difficultyToDto(user));
        }
        return difficulties;
    }

    private void setupConnection() throws SQLException {

        System.out.println("Connecting to database...");
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
        System.out.println("Database connection established");

        flyway = Flyway.configure().dataSource(DB_URL, USER, PASS).load();
        flyway.clean();
        flyway.configure().baselineOnMigrate(true);
        flyway.migrate();
    }
}
