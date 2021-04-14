package server.websocketTest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import service.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import dto.QuestionDto;
import dto.QuestionQueryDto;

@Controller
@RequiredArgsConstructor
@Slf4j
public class QuestionController {

    private final QuestionService instance;

    @MessageMapping("/question")
    @SendTo("/topic/questions")
    public QuestionDto randomQuestionDto(QuestionQueryDto questionDtoQuery) {
        return instance.getRandomQuestion(
                questionDtoQuery.getTopic(),
                questionDtoQuery.getDifficulty());
    }

}