package cn.edu.xjtu.se.jackq.libmgmt.dao;

import cn.edu.xjtu.se.jackq.libmgmt.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        User user = (User) currentSession.load(User.class, userId);
        if(null != user) {
            currentSession.delete(user);
        }
    }

    @Override
    public User getUserById(int userId) {

        Session currentSession = sessionFactory.getCurrentSession();
        return (User) currentSession.load(User.class, userId);
    }

    @Override
    public User getUserByName(String name) {
        Session currentSession = sessionFactory.getCurrentSession();
//        User user = currentSession.createQuery(
//                "select u from User u where u.userName = :name").setParameter("name", name).get
        User user = (User) currentSession.bySimpleNaturalId(User.class).load(name);

        return user;
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
    public List<User> listUser() {
        Session currentSession = this.sessionFactory.getCurrentSession();
        return (List<User>) currentSession.createQuery("from User").list();
    }
}
