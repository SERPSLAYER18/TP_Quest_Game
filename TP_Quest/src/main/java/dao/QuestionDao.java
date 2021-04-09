package dao;

import dao.domain.Question;

import java.util.List;

public interface QuestionDao extends Dao<Question> {

    long getTopicIndex(String topic);

    long getDifficultyIndex(String difficulty);

    List<Question> getQuestions(long topicId, long difficultyId);

    List<Question> getQuestions(String topic, String difficulty);

    Question getRandomQuestion(String topic, String difficulty);

    String getCorrectAnswer(Question newQuestion);

    String getCorrectAnswer(long id);
}
