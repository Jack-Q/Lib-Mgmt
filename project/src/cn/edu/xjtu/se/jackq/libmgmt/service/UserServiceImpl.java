package cn.edu.xjtu.se.jackq.libmgmt.service;

import cn.edu.xjtu.se.jackq.libmgmt.dao.UserDao;
import cn.edu.xjtu.se.jackq.libmgmt.entity.User;
import cn.edu.xjtu.se.jackq.libmgmt.entity.UserRole;
import cn.edu.xjtu.se.jackq.libmgmt.session.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.security.MessageDigest;
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
        // default role of user is Guest
        addRole(user, UserRole.GUEST);
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
        return userDao.listUser(UserDao.LIST_ALL_USER);
    }

    @Override
    public List<User> listReader() {
        return userDao.listUser(UserDao.LIST_READER);
    }

    @Override
    public List<User> listLibrarian() {
        return userDao.listUser(UserDao.LIST_LIBRARIAN);
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
        // Password hash digest
        // reference to http://stackoverflow.com/questions/3103652/hash-string-via-sha-256-in-java
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(password.getBytes("UTF-8"));
            byte[] digest = messageDigest.digest();
            return String.format("%064x", new BigInteger(1, digest));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred while create hashed password, fallback to plain password");
            return password;
        }
    }

    @Override
    public boolean doLogout(SessionUser sessionUser) {
        if (sessionUser != null) {
            sessionUser.setAuthorized(false);
            sessionUser.setUserName(null);
            sessionUser.setId(0);
            sessionUser.setName(null);
            sessionUser.setRoles(new ArrayList<>());
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

    @Override
    public boolean isInRole(int userId, UserRole userRole) {
        if (userId <= 0) return false;
        if (userRole == null) return false;

        User user = userDao.getUserById(userId);
        return user != null && user.getRoles().contains(userRole);

    }

    @Override
    public boolean setRole(int userId, UserRole userRole) {
        User user = getUser(userId);
        if (userRole == null || user == null) {
            return false;
        }
        user.getRoles().clear();
        user.getRoles().add(userRole);
        return updateUser(user);
    }

    @Override
    public List<User> searchUser(String queryString, boolean isByName, boolean isById) {
        return userDao.searchUser(queryString, isByName, isById);
    }

}
