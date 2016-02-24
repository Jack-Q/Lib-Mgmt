package cn.edu.xjtu.se.jackq.libmgmt.dao;

import cn.edu.xjtu.se.jackq.libmgmt.entity.Book;

import java.util.List;

/**
 * Created by Jack on 2/14/2016.
 */
public interface BookDao {
    Book addBook(Book book);

    Book updateBook(Book book);

    boolean removeBook(Book book);

    Book getBookById(int id);
    Book getBookByBookCode(String bookCode);

    List<Book> listBook();
}
