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
    public long getTopicIndex(String topic) {
        return questionDAO.getTopicIndex(topic);
    }

    @Override
    public long getDifficultyIndex(String difficulty) {
        return questionDAO.getDifficultyIndex(Integer.parseInt(difficulty));
    }

    @Override
    public String getCorrectAnswer(long id) {
        return questionDAO.getCorrectAnswer(id);
    }

    @Override
    public QuestionDto getRandomQuestion(String topic, String difficulty) {
        return QuestionMapper.questionToDto(questionDAO.getRandomQuestion(getTopicIndex(topic), getDifficultyIndex(difficulty)));
    }

    @Override
    public QuestionDto getRandomQuestion(long topicId, long difficultyId) {
        return QuestionMapper.questionToDto(questionDAO.getRandomQuestion(topicId, difficultyId));
    }

    @Override
    public boolean compareCorrectAnswer(long questionId, String userAnswer) {
        return questionDAO.compareCorrectAnswer(questionId, userAnswer);
    }
}

