package cn.edu.xjtu.se.jackq.libmgmt.service;


import cn.edu.xjtu.se.jackq.libmgmt.entity.Book;
import cn.edu.xjtu.se.jackq.libmgmt.entity.BookLoan;
import cn.edu.xjtu.se.jackq.libmgmt.entity.User;

import java.util.List;

public interface BookService {
    Book getBook(int id);

    Book getBook(String bookCode);

    Book addBook(Book book);

    Book updateBook(Book book);

    boolean addBookCopies(int bookId, int count);

    List<Book> listBook();

    List<Book> findBook(String query);

    boolean lendBook(User user, Book book, int days);

    List<BookLoan> listLoanBook(User user);
}
