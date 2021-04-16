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

        return (jdbcTemplate.query("select * from game_user where name=:name and password=:password", map, (resultSet, rowNum) ->
                new User(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3).toCharArray(),
                        resultSet.getInt(4)))).get(0);

    }

    @Override
    public User get(long id) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);

        return (jdbcTemplate.query("select * from game_user where id=:id", map, (resultSet, rowNum) ->
                new User(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3).toCharArray(),
                        resultSet.getInt(4)))).get(0);

    }


    @Override
    public int getRecord(long id) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);

        return (jdbcTemplate.query("select score from game_user where id=:id", map, (resultSet, rowNum) ->
                new Integer(resultSet.getInt(4)))).get(0);

    }

    @Override
    public void save(User user) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", user.getName());
        map.put("password", user.getPassword());
        map.put("id", user.getScore());
        jdbcTemplate.update("insert into game_user (name,password,score) values (:name,:password,:score)", map);

    }

    @Override
    public void update(long id, String[] params) {

        User user = get(id);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", params[0] == null ? user.getId() : Long.parseLong(params[0]));
        map.put("name", params[1] == null ? user.getName() : params[1]);
        map.put("password", params[2] == null ? user.getPassword() : params[2]);
        map.put("score", params[3] == null ? user.getScore() : Integer.parseInt(params[3]));
        jdbcTemplate.update("update game_user set name=:name, password=:password, score=:score where id=:id", map);

    }

    @Override
    public void delete(long id) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        jdbcTemplate.update("delete from game_user where id=:id", map);

    }

}
