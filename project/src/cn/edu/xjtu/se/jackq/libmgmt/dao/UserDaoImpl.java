package cn.edu.xjtu.se.jackq.libmgmt.dao;

import cn.edu.xjtu.se.jackq.libmgmt.entity.User;
import cn.edu.xjtu.se.jackq.libmgmt.entity.UserRole;
import org.hibernate.Query;
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
                query = currentSession.createQuery("from User as user where some elements(user.roles) in :roleList");
                query.setString("roleList", UserRole.LIBRARIAN.toString());
                break;
            case LIST_READER:
                query = currentSession.createQuery("from User as user where some elements(user.roles) in :roleList");
                query.setParameterList("roleList", new String[]{UserRole.STUDENT.toString(), UserRole.GUEST.toString()});
                break;
            case LIST_ALL_USER:
            default:
                query = currentSession.createQuery("from User");
                break;
        }
        return (List<User>) query.list();
    }

}
