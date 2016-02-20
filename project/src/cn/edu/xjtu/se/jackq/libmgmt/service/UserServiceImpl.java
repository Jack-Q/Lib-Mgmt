package cn.edu.xjtu.se.jackq.libmgmt.service;

import cn.edu.xjtu.se.jackq.libmgmt.dao.UserDao;
import cn.edu.xjtu.se.jackq.libmgmt.entity.User;
import cn.edu.xjtu.se.jackq.libmgmt.entity.UserRole;
import cn.edu.xjtu.se.jackq.libmgmt.session.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User addUser(String username, String password, String name) {
        User user = new User();
        user.setUserName(username);
        user.setPasswordHash(hashPassword(password));
        user.setName(name);
        userDao.addUser(user);
        return user;
    }

    @Override
    public User getUser(String userName) {
        return userDao.getUserByName(userName);
    }

    @Override
    @Transactional
    public User getUser(int id) {
        return userDao.getUserById(id);
    }

    @Override
    @Transactional
    public void remove(User user) {
        userDao.removeUser(user);
    }

    @Override
    public List<User> listUser() {
        return userDao.listUser();
    }

    @Override
    public boolean doLogin(String username, String password, SessionUser sessionUser) {
        User user = userDao.getUserByName(username);
        String hashedPassword = hashPassword(password);
        if (null != user && user.getPasswordHash().equals(hashedPassword)) {
            sessionUser.setAuthorized(true);
            sessionUser.setUserName(username);
            sessionUser.setName(user.getName());
            sessionUser.setId(user.getId());
            sessionUser.setRoles(user.getRoles());

            return true;
        }
        return false;
    }

    @Override
    public String hashPassword(String password) {
        return password;
    }

    @Override
    public boolean doLogout(SessionUser sessionUser) {
        if (sessionUser != null) {
            sessionUser.setAuthorized(false);
            sessionUser.setUserName(null);
            sessionUser.setId(0);
            sessionUser.setName(null);
        }
        return true;
    }

    @Override
    public boolean checkNameAvailability(String userName) {

        return null == userDao.getUserByName(userName);
    }

    @Override
    public boolean updateUser(User user) {
        userDao.updateUser(user);
        return true;
    }

    @Override
    public boolean checkPassword(int id, String currentPassword) {
        User user = userDao.getUserById(id);
        String hashPassword = hashPassword(currentPassword);

        return null != user && user.getPasswordHash().equals(hashPassword);
    }

    @Override
    public boolean changePassword(int id, String newPassword) {
        User user = userDao.getUserById(id);
        String hashPassword = hashPassword(newPassword);
        if (user == null) {
            return false;
        }

        if (newPassword == null || newPassword.isEmpty()) {
            return false;
        }

        user.setPasswordHash(hashPassword);
        return userDao.updateUser(user);
    }


    public boolean addRole(User user, UserRole role) {
        if (user == null || role == null) {
            return false;
        }

        List<UserRole> roles = user.getRoles();
        if (roles == null) {
            roles = new ArrayList<>();
        }
        roles.add(role);
        user.setRoles(roles);
        return userDao.updateUser(user);

    }

    public void signIn(User user) {

        user.getUserName();
    }
}
