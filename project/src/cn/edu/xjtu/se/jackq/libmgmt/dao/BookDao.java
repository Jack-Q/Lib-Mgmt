package cn.edu.xjtu.se.jackq.libmgmt.dao;

import cn.edu.xjtu.se.jackq.libmgmt.entity.Book;

/**
 * Created by Jack on 2/14/2016.
 */
public interface BookDao {
    void addBook(Book book);
    void updateBook(Book book);
    void removeBook(Book book);

    Book getBookById(int id);
    Book getBookByBookCode(String bookCode);


}
