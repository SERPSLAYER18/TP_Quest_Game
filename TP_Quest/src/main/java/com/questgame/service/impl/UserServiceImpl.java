package com.questgame.service.impl;

import com.questgame.dao.UserDao;
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
    public int getScore(long id) {
        return userDAO.getScore(id);
    }


    @Override
    public void save(String name, String password) {
        userDAO.save(name, password);
    }

    @Override
    public void updatePassword(long id, String password) {
        userDAO.updatePassword(id, password);
    }

    @Override
    public void updateUserName(long id, String username) {
        userDAO.updateUserName(id, username);
    }

    @Override
    public void updateScore(long id, int score) {
        userDAO.updateScore(id, score);
    }


    @Override
    public void delete(long id) {
        userDAO.delete(id);
    }

}
