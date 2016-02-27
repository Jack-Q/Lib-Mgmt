package cn.edu.xjtu.se.jackq.libmgmt.service;


import cn.edu.xjtu.se.jackq.libmgmt.entity.Book;
import cn.edu.xjtu.se.jackq.libmgmt.entity.BookLoan;
import cn.edu.xjtu.se.jackq.libmgmt.entity.User;

import java.util.List;

public interface BookService {
    Book getBook(int id);

    Book getBook(String bookCode);

    Book addBook(Book book);

    boolean updateBook(Book book);

    boolean addBookCopies(int bookId, int count);

    List<Book> listBook();

    List<Book> searchBook(String query);

    boolean lendBook(User user, Book book, int days);


    int LIST_LOAN_ALL = 1;
    int LIST_LOAN_CURR = 2;
    int LIST_LOAN_FINISH = 3;

    List<BookLoan> listLoanBook(User user, int listPolicy);


    List<Book> searchBook(String query, boolean byCode, boolean byName, boolean byAuthor);

    List<Book> searchBookToLend(User user, String query, boolean byCode, boolean byName, boolean byAuthor);

    boolean returnBook(int loanId);

    boolean returnBookFined(int loanId, double finedAmount);

    boolean returnBookBroken(int loanId);

    boolean returnBookLost(int loanId);

    boolean extendBookLoan(int loanId, int loanPeriod);

    void commentBook(Book book, User user, String content);

    boolean rateComment(int commentId, int rateChange);
}
