package server.websocketTest;

import service.DBService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import dto.QuestionDto;
import dto.QuestionQueryDto;

@Controller
public class QuestionController {

    @MessageMapping("/QuestionDto")
    @SendTo("/topic/QuestionDtos")
    public QuestionDto randomQuestionDto(QuestionQueryDto questionDtoQuery) throws Exception {
        QuestionDto questionDto = DBService.instance.getRandomQuestion(
                questionDtoQuery.getTopic(),
                questionDtoQuery.getDifficulty());
        return questionDto;
    }

}