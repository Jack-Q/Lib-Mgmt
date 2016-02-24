package cn.edu.xjtu.se.jackq.libmgmt.dao;

import cn.edu.xjtu.se.jackq.libmgmt.entity.Book;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Jack on 2/14/2016.
 */
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
}
