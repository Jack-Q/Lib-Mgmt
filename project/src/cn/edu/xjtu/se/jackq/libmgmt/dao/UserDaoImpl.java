package cn.edu.xjtu.se.jackq.libmgmt.dao;

import cn.edu.xjtu.se.jackq.libmgmt.entity.User;
import cn.edu.xjtu.se.jackq.libmgmt.entity.UserRole;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addUser(User user) {
        sessionFactory.getCurrentSession().persist(user);
    }

    @Override
    public void removeUser(User user) {
        sessionFactory.getCurrentSession().delete(user);
    }

    @Override
    public void removeUser(int userId) {
        Session currentSession = sessionFactory.getCurrentSession();
        User user = (User) currentSession.get(User.class, userId);
        if (null != user) {
            currentSession.delete(user);
        }
    }

    @Override
    public User getUserById(int userId) {
        Session currentSession = sessionFactory.getCurrentSession();
        return (User) currentSession.get(User.class, userId);
    }

    @Override
    public User getUserByName(String name) {
        Session currentSession = sessionFactory.getCurrentSession();
//        User user = currentSession.createQuery(
//                "select u from User u where u.userName = :name").setParameter("name", name).get

        return (User) currentSession.bySimpleNaturalId(User.class).load(name);
    }

    @Override
    public void updateNameById(int id, String newName) {

    }

    @Override
    public void updatePasswordById(int id, String password) {

    }

    @Override
    public boolean updateUser(User user) {
        Session currentSession = this.sessionFactory.getCurrentSession();
        currentSession.update(user);
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> listUser(int listPolicy) {
        Session currentSession = this.sessionFactory.getCurrentSession();
        Query query;
        switch (listPolicy) {
            case LIST_LIBERIAN:
                query = currentSession.createQuery("from User user join user.roles role  where role in :roleList");
                query.setParameterList("roleList", Collections.singletonList(UserRole.LIBRARIAN));
                break;
            case LIST_READER:
                query = currentSession.createQuery("from User user join user.roles role  where role in :roleList");
                query.setParameterList("roleList", Arrays.asList(UserRole.STUDENT, UserRole.GUEST));
                break;
            case LIST_ALL_USER:
            default:
                query = currentSession.createQuery("from User");
                break;
        }
        List<User> userList;

        try {
            userList = query.list();
        } catch (Exception e) {
            userList = null;
        }

        return userList;
    }

}
