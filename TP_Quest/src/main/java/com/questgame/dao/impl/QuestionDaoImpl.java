package com.questgame.dao.impl;

import com.questgame.dao.QuestionDao;
import com.questgame.dao.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class QuestionDaoImpl implements QuestionDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public long getTopicIndex(String topic) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("topic", topic);

        return (jdbcTemplate.query("select id from topic where name=:topic", map, (resultSet, rowNum) ->
                new Long(resultSet.getLong(1)))).get(0);
    }

    public long getDifficultyIndex(String difficulty) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("score", Integer.parseInt(difficulty));

        return (jdbcTemplate.query("select id from difficulty where score=:score", map, (resultSet, rowNum) ->
                new Long(resultSet.getLong(1)))).get(0);
    }

    @Override
    public Question getQuestion(String text) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("text", text);

        return (jdbcTemplate.query("SELECT q.id,q.text,q.answer,t.name,d.score " +
                "FROM ((questions q " +
                "INNER JOIN topic t ON t.id = q.id_topic) " +
                "INNER JOIN difficulty d ON d.id = q.id_difficulty) " +
                "WHERE q.text=:text", map, (resultSet, rowNum) ->
                new Question(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)))).get(0);
    }

    @Override
    public Question getRandomQuestion(String topic, String difficulty) {

        long difficultyId, topicId;

        topicId = getTopicIndex(topic);
        difficultyId = getDifficultyIndex(difficulty);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("topicId", topicId);
        map.put("difficultyId", difficultyId);

        return (jdbcTemplate.query("SELECT q.id,q.text,q.answer,t.name,d.score " +
                "FROM ((questions q " +
                "INNER JOIN topic t ON t.id=:topicId AND q.id_topic=:topicId) " +
                "INNER JOIN difficulty d ON d.id=:difficultyId AND q.id_difficulty=:difficultyId) " +
                "ORDER BY RANDOM() " +
                "LIMIT 1", map, (resultSet, rowNum) ->
                new Question(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        Integer.toString(resultSet.getInt(5))))).get(0);

    }

    @Override
    public String getCorrectAnswer(long id) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);

        return (jdbcTemplate.query("select answer from questions where id=:id", map, (resultSet, rowNum) ->
                new String(resultSet.getString(1)))).get(0);
    }

    @Override
    public boolean compareCorrectAnswer(String questionText, String userAnswer) {

        Question question = getQuestion(questionText);
        return getCorrectAnswer(question.getId()) == userAnswer;

    }
}
