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
        Book book = (Book) currentSession.get(Book.class, id);
        return book;
    }

    @Override
    public Book getBookByBookCode(String bookCode) {
        Session currentSession = sessionFactory.getCurrentSession();
        Book book = (Book) currentSession.bySimpleNaturalId(Book.class).load(bookCode);
        return book;
    }

    @Override
    public List<Book> listBook() {
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("from Book");
        List bookList = query.list();
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
        return query.list();
    }

    @Override
    public void addComment(BookComment bookComment) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.persist(bookComment);
    }

    @Override
    public BookComment getComment(int commentId) {
        Session currentSession = sessionFactory.getCurrentSession();
        BookComment bookComment = (BookComment) currentSession.get(BookComment.class, commentId);
        return bookComment;
    }

    @Override
    public boolean updateBookComment(BookComment bookComment) {

        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(bookComment);
        return true;
    }
}
