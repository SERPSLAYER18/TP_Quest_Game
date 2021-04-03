package server.websocketTest;

import JDBC.DBService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import server.DTO.QuestionDTO;
import server.DTO.QuestionQueryDTO;

@Controller
public class QuestionController {

    @MessageMapping("/question")
    @SendTo("/topic/questions")
    public QuestionDTO randomQuestion(QuestionQueryDTO questionQuery) throws Exception {
        QuestionDTO question = DBService.instance.getRandomQuestion(questionQuery.getTopic(),questionQuery.getDifficulty());
        return question;
    }

}