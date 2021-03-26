
import DataSets.*;
import JDBC.DBService;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws SQLException {
        DBService dbService = new DBService();

        dbService.saveUser( new UserData("2","132"));

        UserData userforname = dbService.getUser("1","12");
        System.out.println(userforname.toString());

        System.out.println(dbService.getUserRecord(37));

        dbService.updateUserRecord(39,12);

        ArrayList<UserData> list = dbService.getUsers(userData -> true);


        for( var user:list)
        {
            System.out.println(user.toString());
        }

        ArrayList<NewQuestionData> list1 = dbService.getQuestions(1,3);

        for( var user:list1)
        {
            System.out.println(user.toString());
        }

        System.out.println(dbService.getCorrectAnswer(1));

        dbService.saveTopic(new TopicData("Природа"));

        dbService.saveQuestion(new NewQuestionData("Небеса?","Нет","История","100"));

        ArrayList<NewQuestionData> list2 = dbService.getQuestions("История","100");
        //ArrayList<TopicData> list2 = dbService.getTopics(topicData -> true);

        for( var user:list2)
        {
            System.out.println(user.toString());
        }

        ArrayList<NewQuestionData> list3 = dbService.getQuestions("Культура и традиции","200");

        for( var user:list3)
        {
            System.out.println(user.toString());
        }
    }
}
