package com.questgame.dao.impl;

import com.questgame.dao.UserDao;
import com.questgame.dao.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public User get(String name, String password) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("password", password);

        return jdbcTemplate.queryForObject("select * from game_user where name=:name and password=:password", map, (resultSet, rowNum) ->
                new User(resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("password").toCharArray(),
                        resultSet.getInt("score")));

    }

    @Override
    public User get(long id) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);

        return jdbcTemplate.queryForObject("select * from game_user where id=:id", map, (resultSet, rowNum) ->
                new User(resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("password").toCharArray(),
                        resultSet.getInt("score")));

    }

    @Override
    public int getScore(long id) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);

        return jdbcTemplate.queryForObject("select score from game_user where id=:id", map, (resultSet, rowNum) ->
                new Integer(resultSet.getInt("score")));

    }

    @Override
    public void save(String name, String password) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("password", password);
        jdbcTemplate.update("insert into game_user (name,password) values (:name,:password)", map);

    }

    @Override
    public void updatePassword(long id, String password) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("password", password);
        jdbcTemplate.update("update game_user set password=:password where id=:id", map);

    }

    @Override
    public void updateUserName(long id, String username) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("name", username);
        jdbcTemplate.update("update game_user set name=:name where id=:id", map);

    }

    @Override
    public void updateScore(long id, int score) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("score", score);
        jdbcTemplate.update("update game_user set score=:score where id=:id", map);

    }

    @Override
    public void delete(long id) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        jdbcTemplate.update("delete from game_user where id=:id", map);

    }

}
