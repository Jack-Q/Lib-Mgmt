package cn.edu.xjtu.se.jackq.libmgmt.service;

import cn.edu.xjtu.se.jackq.libmgmt.entity.User;
import cn.edu.xjtu.se.jackq.libmgmt.entity.UserRole;
import cn.edu.xjtu.se.jackq.libmgmt.session.SessionUser;

import java.util.List;


public interface UserService {
    User addUser(String username, String password, String name);

    User getUser(String userName);

    User getUser(int id);

    void remove(User user);

    List<User> listUser();

    List<User> listReader();

    List<User> listLibrarian();

    boolean doLogin(String username, String password, SessionUser sessionUser);

    String hashPassword(String password);

    boolean doLogout(SessionUser sessionUser);

    boolean checkNameAvailability(String userName);

    boolean updateUser(User user);

    boolean checkPassword(int id, String currentPassword);

    boolean changePassword(int id, String newPassword);

    boolean addRole(User user, UserRole userRole);

    boolean isInRole(int userId, UserRole userRole);

    boolean setRole(int userId, UserRole userRole);

    List<User> searchUser(String queryString, boolean isByName, boolean isById);
}