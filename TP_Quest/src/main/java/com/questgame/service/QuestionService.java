package com.questgame.service;

import com.questgame.dto.QuestionDto;

public interface QuestionService {

    String getCorrectAnswer(String questionText);

    QuestionDto getRandomQuestion(String topic, String difficulty);

    boolean compareCorrectAnswer(String questionText, String userAnswer);

}