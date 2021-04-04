package server;


import dto.QuestionDto;
import dto.UserDto;
import service.DBService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FillData {

    public static DBService service;

    public static void fillUsers() throws SQLException {
        List<UserDto> users = new ArrayList<>();
        users.add(new UserDto("Kirill", "1234--"));
        users.add(new UserDto("Zlata", "11111"));
        users.add(new UserDto("Liza", "рейкик"));
        users.add(new UserDto("Grape", "да"));

        service.cleanUpUsers();
        Random random = new Random();

        for (UserDto user : users) {
            user.setScore(Math.abs(random.nextInt() % 1000));
            service.saveUser(user);
        }


    }

    public static void fillQuestions() throws SQLException {
        ArrayList<QuestionDto> questions = new ArrayList<>();
        questions.add(new QuestionDto("ЕГО поднял над Скалой Прайда мандрил Рафики","Симба","Кино","100"));
        questions.add(new QuestionDto("\"Кинопробы\" ОНИ записали песню Виктора Цоя \"Следи за собой\"","Король и шут","Кино","200"));
        questions.add(new QuestionDto("ОН сыграл главную роль в фильме \"Тюремный рок\"","Элвис Пресли","Кино","300"));
        questions.add(new QuestionDto("ЭТОТ ФИЛЬМ далек от литературного первоисточника. Например, Гудвин там становится убийцей","Короли и капуста","Кино","400"));
        questions.add(new QuestionDto("В фильме 1926 года ОН становится командиром подразделения Рабоче-крестьянской Красной Армии","Беня Крик","Кино","500"));
        questions.add(new QuestionDto("Крокодил Гена пел:\nПрилетит вдруг волшебник\nВ голубом вертолете,\nИ бесплатно покажет кино.\"\n Автор музыки к этой песне..","Владимир Шаинский","Кино","300"));
        questions.add(new QuestionDto("Этот японский поэт Х в. составил первую антологию японской поэзии (достаточно фамилии)","Кино","Кино","200"));
        questions.add(new QuestionDto("Так назывался первый в истории группы \"Кино\" альбом, записанный в профессиональной студии","Звезда по имени Солнце","Кино","100"));
        questions.add(new QuestionDto("В пионерской песне с рефреном \"неправильно, неправильно кончается кино!\" осуждается его страшная гибель.","Чапаева ","Кино","400"));
        questions.add(new QuestionDto(" Именно он был первым ведущим передачи \"Кинопанорама\"","Алексей Каплер.","Кино","500"));
        questions.add(new QuestionDto(" ТАК называется биографический фильм о жизни рок-музыканта Иэна Дьюри","Секс, наркотики и рок-н-ролл","Кино","400"));

        service.clearQuestions();
        for(var q:questions)
        {
            service.saveQuestion(q);
        }

    }

    public static void main(String[] args) throws SQLException {
        service = new DBService();
        fillUsers();
        fillQuestions();
    }
}
