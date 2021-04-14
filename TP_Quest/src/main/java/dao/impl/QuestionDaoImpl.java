package dao.impl;

import dao.QuestionDao;
import dao.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class QuestionDaoImpl implements QuestionDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public long getTopicIndex(String topic) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("topic", topic);

        return (jdbcTemplate.query("select id from topic where name=(:topic)", map, (resultSet, rowNum) ->
                new Long(resultSet.getLong(1)))).get(0);
    }

    public long getDifficultyIndex(String difficulty) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("score", difficulty);

        return (jdbcTemplate.query("select id from difficulty where score=(:difficulty)", map, (resultSet, rowNum) ->
                new Long(resultSet.getLong(1)))).get(0);
    }


    public List<Question> getQuestions(long topicId, long difficultyId) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("topicId", topicId);
        map.put("difficultyId", difficultyId);

        return jdbcTemplate.query("SELECT q.id,q.text,q.answer,t.name,d.score FROM ((questions q " +
                "INNER JOIN topic t ON t.id=(:topicId) and q.id_topic=(:topicId)) " +
                "INNER JOIN difficulty d ON d.id=(:difficultyId) and q.id_difficulty=(:difficultyId))", map, (resultSet, rowNum) ->
                new Question(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)));

    }

    public List<Question> getQuestions(String topic, String difficulty) {

        long difficultyId, topicId;

        topicId = getTopicIndex(topic);
        difficultyId = getDifficultyIndex(difficulty);

        return getQuestions(topicId, difficultyId);
    }

    public Question getRandomQuestion(String topic, String difficulty) {

        long difficultyId, topicId;

        topicId = getTopicIndex(topic);
        difficultyId = getDifficultyIndex(difficulty);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("topicId", topicId);
        map.put("difficultyId", difficultyId);

        return (jdbcTemplate.query("SELECT q.id,q.text,q.answer,t.name,d.score " +
                "FROM ((questions q " +
                "INNER JOIN topic t ON t.id=(:topicId)) " +
                "INNER JOIN difficulty d ON d.id=(:difficultyId)) " +
                "ORDER BY RANDOM() " +
                "LIMIT 1", map, (resultSet, rowNum) ->
                new Question(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)))).get(0);

    }

    public String getCorrectAnswer(Question newQuestion) {

        return getCorrectAnswer(newQuestion.getId());

    }

    public String getCorrectAnswer(long id) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);

        return (jdbcTemplate.query("select answer from questions where id=(:id)", map, (resultSet, rowNum) ->
                new String(resultSet.getString(1)))).get(0);
    }

    @Override
    public Question get(long id) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);

        return (jdbcTemplate.query("SELECT q.id,q.text,q.answer,t.name,d.score " +
                "FROM ((questions q " +
                "INNER JOIN topic t ON t.id = q.id_topic) " +
                "INNER JOIN difficulty d ON d.id = and q.id_difficulty) " +
                "WHERE q.id=(:id)", map, (resultSet, rowNum) ->
                new Question(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)))).get(0);
    }

    @Override
    public List<Question> get(Predicate<Question> predicate) throws SQLException {

        return jdbcTemplate.query("SELECT q.id,q.text,q.answer,t.name,d.score " +
                "FROM ((questions q " +
                "INNER JOIN topic t ON t.id = q.id_topic) " +
                "INNER JOIN difficulty d ON d.id = and q.id_difficulty) ", (resultSet, rowNum) ->
                new Question(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)));

    }

}
