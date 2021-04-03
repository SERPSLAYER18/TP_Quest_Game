package JDBC;

import DataSets.*;
import JDBC.DAO.*;

import org.flywaydb.core.Flyway;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.Predicate;

public class DBService {

    private static Flyway flyway;
    public static DBService instance;

    static {
        try {
            instance  = new DBService();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static final String JDBC_DRIVER = "org.postgresql.Driver";
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/quest_game_db";
    public static final String USER = "postgres";
    public static final String PASS = "password";

    private Connection connection = null;
    private UserDAO userDAO = null;
    private QuestionDAO questionDAO = null;
    private TopicDAO topicDAO = null;
    private DifficultyDAO difficultyDAO = null;
    private NewQuestionDAO newQuestionDAO = null;

    public DBService() throws SQLException {
        setupConnection();
        userDAO = new UserDAO(connection);
        topicDAO = new TopicDAO(connection);
        difficultyDAO = new DifficultyDAO(connection);
        questionDAO = new QuestionDAO(connection);
        newQuestionDAO = new NewQuestionDAO(connection);
    }


    public ArrayList<UserData> getUsers(Predicate<UserData> predicate) throws SQLException
    {
        return userDAO.get(predicate);
    }

    public UserData getUser(String name,String password) throws SQLException
    {
        return userDAO.get(name,password);
    }

    public UserData getUser(long id)
    {
        return userDAO.get(id);
    }

    public int getUserRecord(long id){

        return userDAO.getRecord(id);
    }

    public void updateUserRecord(long id, int newRecord) throws SQLException{

        try {
        UserData user = userDAO.get(id);
        user.setScore(newRecord);
        userDAO.update(user, new String[]{});
        }
        catch (Exception e)
        {
            connection.rollback();
        }
        finally {
            connection.setAutoCommit(true);
        }
    }

    public void saveUser(UserData userData) throws SQLException{
        try {
            connection.setAutoCommit(false);
            userDAO.createTable();
            userDAO.save(userData);
            connection.commit();
        }
        catch (Exception e)
        {
            connection.rollback();
        }
        finally {
            connection.setAutoCommit(true);
        }

    }

    public void updateUser(UserData userData, String[] params) throws SQLException
    {
        try {
            connection.setAutoCommit(false);
            userDAO.update(userData,params);
            connection.commit();
        }
        catch (Exception e)
        {
            connection.rollback();
        }
        finally {
            connection.setAutoCommit(true);
        }

    }

    public void deleteUser(UserData userData) throws SQLException {
        try {
            connection.setAutoCommit(false);
            userDAO.delete(userData);
            connection.commit();
        }
        catch (Exception e)
        {
            connection.rollback();
        }
        finally {
            connection.setAutoCommit(true);
        }
    }

    public void cleanUpUsers() throws SQLException {
        try {
            connection.setAutoCommit(false);
            userDAO.dropTable();
            connection.commit();
        }
        catch (Exception e)
        {
            connection.rollback();
        }
        finally {
            connection.setAutoCommit(true);
        }
    }



    public ArrayList<QuestionData>getQuestions(long id_topic, long id_difficulty)
    {
        return questionDAO.getQuestions(id_topic,id_difficulty);
    }

    public String getCorrectAnswer(long id)
    {
        return questionDAO.getCorrectAnswer(id);
    }

    public void saveQuestion(NewQuestionData questionData) throws SQLException{
        try {
            connection.setAutoCommit(false);
            newQuestionDAO.createTable();
            newQuestionDAO.save(questionData);
            connection.commit();
        }
        catch (Exception e)
        {
            connection.rollback();
        }
        finally {
            connection.setAutoCommit(true);
        }

    }

    public void deleteQuestion(NewQuestionData questionData) throws SQLException {
        try {
            connection.setAutoCommit(false);
            newQuestionDAO.delete(questionData);
            connection.commit();
        }
        catch (Exception e)
        {
            connection.rollback();
        }
        finally {
            connection.setAutoCommit(true);
        }
    }


    public NewQuestionData getRandomQuestion(String topic, String difficulty){
       return newQuestionDAO.getRandomQuestion(topic,difficulty);
    }

    public  void clearQuestions(){
        try{
        newQuestionDAO.clearTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    public ArrayList<TopicData> getTopics(Predicate<TopicData> predicate) throws SQLException{
        return topicDAO.get(predicate);
    }

    public void saveTopic(TopicData topicData) throws SQLException{
        try {
            connection.setAutoCommit(false);
            topicDAO.createTable();
            topicDAO.save(topicData);
            connection.commit();
        }
        catch (Exception e)
        {
            connection.rollback();
        }
        finally {
            connection.setAutoCommit(true);
        }

    }

    public void deleteTopic(TopicData topicData) throws SQLException {
        try {
            connection.setAutoCommit(false);
            topicDAO.delete(topicData);
            connection.commit();
        }
        catch (Exception e)
        {
            connection.rollback();
        }
        finally {
            connection.setAutoCommit(true);
        }
    }



    public ArrayList<DifficultyData> getDifficulties(Predicate<DifficultyData> predicate) throws SQLException{
        return difficultyDAO.get(predicate);
    }

    public void saveDifficulty(DifficultyData difficultyData) throws SQLException{
        try {
            connection.setAutoCommit(false);
            difficultyDAO.createTable();
            difficultyDAO.save(difficultyData);
            connection.commit();
        }
        catch (Exception e)
        {
            connection.rollback();
        }
        finally {
            connection.setAutoCommit(true);
        }

    }

    public void deleteDifficulty(DifficultyData difficultyData) throws SQLException{
        try {
            connection.setAutoCommit(false);
            difficultyDAO.delete(difficultyData);
            connection.commit();
        }
        catch (Exception e)
        {
            connection.rollback();
        }
        finally {
            connection.setAutoCommit(true);
        }

    }

    private void setupConnection() throws SQLException {

        System.out.println("Connecting to database...");
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
        System.out.println("Database connection established");

        flyway = Flyway.configure().dataSource(DB_URL, USER, PASS).load();
        flyway.configure().baselineOnMigrate(true);
        flyway.migrate();
    }
}
