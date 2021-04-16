package com.questgame.dao;

import com.questgame.dao.domain.Question;

public interface QuestionDao {

    long getTopicIndex(String topic);

    long getDifficultyIndex(String difficulty);

    Question getQuestion(String text);

    Question getRandomQuestion(String topic, String difficulty);

    String getCorrectAnswer(long id);

    boolean compareCorrectAnswer(String questionText, String userAnswer);
}
