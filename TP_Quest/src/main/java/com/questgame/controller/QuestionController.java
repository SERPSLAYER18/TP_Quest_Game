package com.questgame.controller;

import com.questgame.dto.QuestionDto;
import com.questgame.dto.QuestionQueryDto;
import com.questgame.service.impl.QuestionServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

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