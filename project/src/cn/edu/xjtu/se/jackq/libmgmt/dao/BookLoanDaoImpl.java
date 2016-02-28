package cn.edu.xjtu.se.jackq.libmgmt.dao;

import cn.edu.xjtu.se.jackq.libmgmt.entity.Book;
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

        @SuppressWarnings("unchecked") List<BookLoan> list = query.list();
        return list;
    }

    @Override
    public List<Book> searchBookToLend(User user, String search, boolean byCode, boolean byName, boolean byAuthor) {
        Session currentSession = sessionFactory.getCurrentSession();
        String queryString = "from Book b fetch all properties " +
                "where (select count(id) from b.bookCopies c where c.status = 'ON_SHELF') > 0 and (" +
                (byCode ? "b.id in (select id from Book book where book.bookCode like :search order by yearOfPublish) " : "") +
                (byCode && byName ? "or " : "") +
                (byName ? "b.id in (select id from Book book where book.bookName like :search  order by yearOfPublish) " :
                        (byCode && byAuthor ? "or " : "")) +
                (byName && byAuthor ? "or " : "") +
                (byAuthor ? "b.id in (select id from Book book where book.author like :search  order by yearOfPublish) " : "")
                + ") and (select count(id) from BookLoan loan where loan.finished = false and loan.user = :user" +
                " and loan.bookCopy.id in (select id from b.bookCopies)) = 0";
        Query query = currentSession.createQuery(queryString);
        query.setParameter("search", "%" + search + "%");
        query.setParameter("user", user);
        @SuppressWarnings("unchecked") List<Book> list = query.list();
        return list;
    }

    @Override
    public BookLoan getLoan(int loanId) {
        Session currentSession = sessionFactory.getCurrentSession();
        return (BookLoan) currentSession.get(BookLoan.class, loanId);

    }

    @Override
    public boolean updateLoan(BookLoan bookLoan) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(bookLoan);
        return true;
    }
}
