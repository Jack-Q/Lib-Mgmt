package cn.edu.xjtu.se.jackq.libmgmt.service;

import cn.edu.xjtu.se.jackq.libmgmt.dao.UserDao;
import cn.edu.xjtu.se.jackq.libmgmt.entity.User;
import cn.edu.xjtu.se.jackq.libmgmt.session.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public User addUser(String username, String password, String name) {
        User user = new User();
        user.setUserName(username);
        user.setPasswordHash(hashPassword(password));
        user.setName(username);
        userDao.addUser(user);
        return user;
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

    public void signIn(User user) {

        user.getUserName();
    }
}
