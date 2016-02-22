package cn.edu.xjtu.se.jackq.libmgmt.dao;

import cn.edu.xjtu.se.jackq.libmgmt.entity.User;

import java.util.List;


public interface UserDao {
    void addUser(User user);
    void removeUser(User user);
    void removeUser(int userId);
    User getUserById(int id);
    User getUserByName(String name);
    void updateNameById(int id, String newName);
    void updatePasswordById(int id, String password);
    boolean updateUser(User user);

    int LIST_ALL_USER = 1;
    int LIST_READER = 2;
    int LIST_LIBRARIAN = 3;

    List<User> listUser(int listPolicy);

}
