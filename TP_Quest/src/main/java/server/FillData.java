package server;

import DataSets.NewQuestionData;
import DataSets.UserData;
import JDBC.DBService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class FillData {
    public static void fillUsers() throws SQLException {
        ArrayList<UserData> users = new ArrayList<>();
        users.add(new UserData("Kirill", "12345"));
        users.add(new UserData("Zlata", "11111"));
        users.add(new UserData("Liza", "рейкъявик"));
        users.add(new UserData("Grape", "да"));

        DBService service = new DBService();
        service.cleanUpUsers();
        Random random = new Random();
        for (UserData user : users) {
            user.setScore(random.nextInt() % 1000);
            service.saveUser(user);
        }

    }

    public static void fillQuestions() throws SQLException {
        ArrayList<NewQuestionData> questions = new ArrayList<>();
        questions.add(new NewQuestionData("ЕГО поднял над Скалой Прайда мандрил Рафики","Симба","Кино","10"));
        questions.add(new NewQuestionData("\"Кинопробы\" ОНИ записали песню Виктора Цоя \"Следи за собой\"","Король и шут","Кино","20"));
        questions.add(new NewQuestionData("ОН сыграл главную роль в фильме \"Тюремный рок\"","Элвис Пресли","Кино","30"));
        questions.add(new NewQuestionData("ЭТОТ ФИЛЬМ далек от литературного первоисточника. Например, Гудвин там становится убийцей","Короли и капуста","Кино","40"));
        questions.add(new NewQuestionData("В фильме 1926 года ОН становится командиром подразделения Рабоче-крестьянской Красной Армии","Беня Крик","Кино","50"));
        questions.add(new NewQuestionData("Крокодил Гена пел:\nПрилетит вдруг волшебник\nВ голубом вертолете,\nИ бесплатно покажет кино.\"\n Автор музыки к этой песне..","Владимир Шаинский","Кино","30"));
        questions.add(new NewQuestionData("Этот японский поэт Х в. составил первую антологию японской поэзии (достаточно фамилии)","Кино","Кино","20"));
        questions.add(new NewQuestionData("Так назывался первый в истории группы \"Кино\" альбом, записанный в профессиональной студии","Звезда по имени Солнце","Кино","10"));
        questions.add(new NewQuestionData("В пионерской песне с рефреном \"неправильно, неправильно кончается кино!\" осуждается его страшная гибель.","Чапаева ","Кино","40"));
        questions.add(new NewQuestionData(" Именно он был первым ведущим передачи \"Кинопанорама\"","Алексей Каплер.","Кино","50"));
        questions.add(new NewQuestionData(" ТАК называется биографический фильм о жизни рок-музыканта Иэна Дьюри","Секс, наркотики и рок-н-ролл","Кино","40"));


        DBService service = new DBService();

        service.clearQuestions();
        for(var q:questions)
        {
            service.saveQuestion(q);
        }

    }

    public static void main(String[] args) throws SQLException {
        //fillUsers();
        //fillQuestions();
    }
}
