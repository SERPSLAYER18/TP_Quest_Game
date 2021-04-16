package com.questgame.service.impl;

import com.questgame.dao.UserDao;
import com.questgame.dao.domain.User;
import com.questgame.dto.UserDto;
import com.questgame.mapper.UserMapper;
import com.questgame.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDAO;

    @Override
    public UserDto get(String name, String password) {
        return UserMapper.userToDto(userDAO.get(name, password));
    }


    @Override
    public int getRecord(long id) {
        return userDAO.getRecord(id);
    }


    @Override
    public void save(String name, String password) {
        userDAO.save(new User(-1, name, password.toCharArray(), 0));
    }


    @Override
    public void update(long id, String[] params) {
        userDAO.update(id, params);
    }


    @Override
    public void delete(long id) {
        userDAO.delete(id);
    }

}
