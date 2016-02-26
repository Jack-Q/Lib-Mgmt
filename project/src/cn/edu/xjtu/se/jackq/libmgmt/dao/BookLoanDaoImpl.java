package cn.edu.xjtu.se.jackq.libmgmt.dao;

import cn.edu.xjtu.se.jackq.libmgmt.entity.BookLoan;
import cn.edu.xjtu.se.jackq.libmgmt.entity.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public class BookLoanDaoImpl implements BookLoanDao {


    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public BookLoan add(BookLoan bookLoan) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.persist(bookLoan);
        return bookLoan;
    }

    @Override
    public List<BookLoan> listLoanByUser(User user, int listPolicy) {
        Session currentSession = sessionFactory.getCurrentSession();
        String queryString = "from BookLoan bookLoan where bookLoan.user.id = :user ";

        switch (listPolicy) {
            case LIST_LOAN_CURR:
                queryString += " and bookLoan.finished = false order by deadlineOfReturning ";
                break;
            case LIST_LOAN_FINISH:
                queryString += " and bookLoan.finished = true order by dateOfBorrowing desc";
                break;
            case LIST_LOAN_ALL:
            default:
                queryString += " order by dateOfBorrowing desc";
                break;
        }
        Query query = currentSession.createQuery(queryString);
        query.setParameter("user", user.getId());

        return query.list();

    }
}
