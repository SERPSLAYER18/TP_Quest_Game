package dao;

import dao.domain.Question;

import java.util.ArrayList;

public interface QuestionDao extends Dao<Question> {

    long getTopicIndex(String topic);

    long getDifficultyIndex(String difficulty);

    ArrayList<Question> getQuestions(long id_topic, long id_difficulty);

    ArrayList<Question> getQuestions(String topic, String difficulty);

    Question getRandomQuestion(String topic, String difficulty);

    String getCorrectAnswer(Question newQuestion);

    String getCorrectAnswer(long id);
}
