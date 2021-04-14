package controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import dto.*;
import service.impl.QuestionServiceImpl;

@Controller
@RequiredArgsConstructor
@Slf4j
public class QuestionController {

    private final QuestionServiceImpl instance;

    @MessageMapping("/question")
    @SendTo("/topic/questions")
    public QuestionDto randomQuestionDto(QuestionQueryDto questionDtoQuery) {
        System.out.println(instance.toString());
        return instance.getRandomQuestion(
                questionDtoQuery.getTopic(),
                questionDtoQuery.getDifficulty());
    }

}