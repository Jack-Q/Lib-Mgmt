package cn.edu.xjtu.se.jackq.libmgmt.service;

import cn.edu.xjtu.se.jackq.libmgmt.entity.User;
import cn.edu.xjtu.se.jackq.libmgmt.session.SessionUser;

import java.util.List;

/**
 * Created by Jack on 2/3/2016.
 */
public interface UserService{
    User addUser(String username, String password, String name);
    void remove(User user);
    List<User> listUser();

    boolean doLogin(String username, String password, SessionUser sessionUser);

    String hashPassword(String password);

    boolean doLogout(SessionUser sessionUser);

    boolean checkNameAvailability(String userName);

    boolean updateUser(User user);
}