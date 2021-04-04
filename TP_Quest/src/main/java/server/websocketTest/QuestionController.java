package server.websocketTest;

import service.DBService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import dto.QuestionDto;
import dto.QuestionQueryDto;

@Controller
public class QuestionController {


    @MessageMapping("/question")
    @SendTo("/topic/questions")
    public QuestionDto randomQuestionDto(QuestionQueryDto questionDtoQuery) {
        return DBService.instance.getRandomQuestion(

                questionDtoQuery.getTopic(),
                questionDtoQuery.getDifficulty());
    }

}