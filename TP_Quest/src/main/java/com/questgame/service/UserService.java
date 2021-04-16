package com.questgame.service;

import com.questgame.dto.UserDto;

public interface UserService {

    UserDto get(String name, String password);

    int getRecord(long id);

    void save(String name, String password);

    void update(long id, String[] params);

    void delete(long id);

}
