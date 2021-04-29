package com.questgame.dao.impl;

import com.questgame.dao.TopicDao;
import com.questgame.dao.domain.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TopicDaoImpl implements TopicDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Topic> getTopics() {

        return jdbcTemplate.query("SELECT * FROM topic", (resultSet, rowNum) ->
                new Topic(resultSet.getLong("id"),
                        resultSet.getString("name")));
    }

    @Override
    public List<Topic> getNTopics(int n) {

        Map<String, Integer> sqlInsertParametersMap = new HashMap<String, Integer>();
        sqlInsertParametersMap.put("num", n);

        return jdbcTemplate.query("SELECT * FROM topic " +
                "ORDER BY RANDOM() " +
                "LIMIT :num", sqlInsertParametersMap, (resultSet, rowNum) ->
                new Topic(resultSet.getLong("id"),
                        resultSet.getString("name")));
    }
}
