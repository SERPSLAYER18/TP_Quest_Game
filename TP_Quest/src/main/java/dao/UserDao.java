package dao;

import dao.domain.User;

public interface UserDao extends Dao<User> {

    public User get(String name, String password);

    public int getRecord(long id);

}
