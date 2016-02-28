package cn.edu.xjtu.se.jackq.libmgmt.dao;

import cn.edu.xjtu.se.jackq.libmgmt.entity.Book;
import cn.edu.xjtu.se.jackq.libmgmt.entity.BookComment;
import cn.edu.xjtu.se.jackq.libmgmt.entity.BookCopy;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class BookDaoImpl implements BookDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Book addBook(Book book) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.persist(book);
        return book;
    }

    @Override
    public Book updateBook(Book book) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(book);
        return book;
    }

    @Override
    public boolean removeBook(Book book) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.delete(book);
        return true;
    }

    @Override
    public Book getBookById(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return (Book) currentSession.get(Book.class, id);
    }


    @Override
    public List<Book> listBook() {
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("from Book");
        @SuppressWarnings("unchecked") List<Book> bookList = query.list();
        return bookList;
    }

    @Override
    public boolean AddCopy(BookCopy bookCopy) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.persist(bookCopy);
        return bookCopy.getId() != 0;
    }

    @Override
    public boolean updateBookCopy(BookCopy bookCopy) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(bookCopy);
        return true;
    }

    @Override
    public List<Book> searchBook(String search, boolean byCode, boolean byName, boolean byAuthor) {
        Session currentSession = sessionFactory.getCurrentSession();
        String queryString = "from Book b where " +
                (byCode ? "b.id in (select id from Book book where book.bookCode like :search order by yearOfPublish) " : "") +
                (byCode && byName ? "or " : "") +
                (byName ? "b.id in (select id from Book book where book.bookName like :search  order by yearOfPublish) " :
                        (byCode && byAuthor ? "or " : "")) +
                (byName && byAuthor ? "or " : "") +
                (byAuthor ? "b.id in (select id from Book book where book.author like :search  order by yearOfPublish) " : "");
        Query query = currentSession.createQuery(queryString);
        query.setParameter("search", "%" + search + "%");

        @SuppressWarnings("unchecked") List<Book> list = query.list();
        return list;
    }

    @Override
    public void addComment(BookComment bookComment) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.persist(bookComment);
    }

    @Override
    public BookComment getComment(int commentId) {
        Session currentSession = sessionFactory.getCurrentSession();
        return (BookComment) currentSession.get(BookComment.class, commentId);
    }

    @Override
    public boolean updateBookComment(BookComment bookComment) {

        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(bookComment);
        return true;
    }

    @Override
    public BookCopy getBookCopy(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return (BookCopy) currentSession.get(BookCopy.class, id);
    }

    @Override
    public boolean isBookCodeAvailable(String bookCode) {

        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("select count(id) from Book b where b.bookCode = :code");
        query.setParameter("code", bookCode);
        long num = (long) query.uniqueResult();
        return num == 0;

    }
}
