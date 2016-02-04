package cn.edu.xjtu.se.jackq.libmgmt.service;

import cn.edu.xjtu.se.jackq.libmgmt.entity.User;

import java.util.List;

/**
 * Created by Jack on 2/3/2016.
 */
public interface UserService{
    void addUser(User user);
    void remove(User user);
    List<User> listUser();
}