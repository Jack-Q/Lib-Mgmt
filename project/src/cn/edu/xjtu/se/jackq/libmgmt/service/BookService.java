package cn.edu.xjtu.se.jackq.libmgmt.service;


import cn.edu.xjtu.se.jackq.libmgmt.entity.Book;

import java.util.List;

public interface BookService {
    Book getBook(int id);

    Book getBook(String bookCode);

    Book addBook(Book book);

    Book updateBook(Book book);

    boolean addBookCopies(int bookId, int count);

    List<Book> listBook();
}
