//package server;
//
//import DataSets.NewQuestionData;
//import DataSets.UserData;
//import JDBC.DBService;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//@RestController
//public class ServerController {
//
//    static DBService db = null;
//
//    @GetMapping("/error")
//    public String showErrorPage()
//    {
//        return  "<h1> Error </h1>";
//    }
//
//    @GetMapping("/404")
//    public String notFoundPage()
//    {
//        return  "<h1> NOT FOUND</h1>";
//    }
//
//
//
//
//    @GetMapping("/highscores")
//    public String showHighscores(@RequestParam(value = "maxCount",defaultValue = "10") int maxCount){
//        try {
//            int i = 0;
//            ArrayList<UserData> users = db.getUsers(u->true);
//            StringBuilder builder = new StringBuilder();
//            for(UserData user : users) {
//                builder.append(String.format("<p>Id: %5d    User: %20s    Score: %10d </p>", user.getId(), user.getName(), user.getScore()));
//                if(i++==maxCount-1)
//                    break;
//            }
//            return builder.toString();
//
//        } catch (SQLException throwables) {
//            return notFoundPage();
//        }
//
//    }
//
//    @GetMapping("/question")
//    public String showRandomQuestion(@RequestParam(value = "topic",defaultValue = "None") String topic,
//                                 @RequestParam(value = "difficulty",defaultValue = "None") String difficulty)
//    {
//        NewQuestionData  question;
//        if(topic.equals("None") || difficulty.equals("None"))
//            return showErrorPage();
//
//        question= db.getRandomQuestion(topic,difficulty);
//
//        if(question == null)
//            return notFoundPage();
//
//        return String.format("<h1>Случайный вопрос<h1>\n<h2>Тема: %s</h2>\n<h2>Сложность: %s</h2>\n<h2>ID вопроса: %s</h2>\n<h3>%s</h3>\n",
//                question.getTopic(),
//                question.getDifficulty(),
//                question.getId(),
//                question.getText());
//    }
//
//}
