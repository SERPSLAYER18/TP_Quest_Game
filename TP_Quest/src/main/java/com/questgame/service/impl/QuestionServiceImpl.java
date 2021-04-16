package com.questgame.service.impl;

import com.questgame.dao.QuestionDao;
import com.questgame.dto.QuestionDto;
import com.questgame.mapper.QuestionMapper;
import com.questgame.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao questionDAO;

    @Override
    public String getCorrectAnswer(String questionText) {
        return questionDAO.getCorrectAnswer(questionDAO.getQuestion(questionText).getId());
    }

    @Override
    public QuestionDto getRandomQuestion(String topic, String difficulty) {
        return QuestionMapper.questionToDto(questionDAO.getRandomQuestion(topic, difficulty));
    }


    @Override
    public boolean compareCorrectAnswer(String questionText, String userAnswer) {
        return questionDAO.compareCorrectAnswer(questionText, userAnswer);
    }
}

