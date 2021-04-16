package com.questgame.dao;

import com.questgame.dao.domain.User;

public interface UserDao {

    User get(String name, String password);

    User get(long id);

    int getRecord(long id);

    void save(User User);

    void update(long id, String[] params);

    void delete(long id);
}
