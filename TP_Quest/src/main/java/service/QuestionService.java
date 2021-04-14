package service;

import dto.QuestionDto;

import java.util.List;

public interface QuestionService {
    List<QuestionDto> getQuestions(long id_topic, long id_difficulty);

    String getCorrectAnswer(long id);

    QuestionDto getRandomQuestion(String topic, String difficulty);
}