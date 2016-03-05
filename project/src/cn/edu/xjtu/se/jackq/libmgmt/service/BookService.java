package cn.edu.xjtu.se.jackq.libmgmt.service;


import cn.edu.xjtu.se.jackq.libmgmt.entity.*;
import cn.edu.xjtu.se.jackq.libmgmt.viewmodel.PageList;

import java.util.List;

public interface BookService {
    int LIST_LOAN_ALL = 1;
    int LIST_LOAN_CURR = 2;
    int LIST_LOAN_FINISH = 3;

    Book getBook(int id);

    BookCopy getBookCopy(int id);

    Book addBook(Book book);

    void addBookAndCopy(String name, String code, String author, String isbn, String publisher, Integer yearOfPublish, String description, Integer copy);

    boolean updateBook(Book book);

    boolean addBookCopies(int bookId, int count);

    List<Book> listBook();

    List<Book> searchBook(String query);

    boolean lendBook(User user, Book book, int days);

    List<BookLoan> listLoanBook(User user, int listPolicy);


    List<Book> searchBook(String query, boolean byCode, boolean byName, boolean byAuthor);

    List<Book> searchBookToLend(User user, String query, boolean byCode, boolean byName, boolean byAuthor);

    boolean returnBook(int loanId);

    boolean returnBookFined(int loanId);

    boolean returnBookBroken(int loanId);

    boolean returnBookLost(int loanId);

    boolean extendBookLoan(int loanId, int loanPeriod);

    void commentBook(Book book, User user, String content);

    void commentBook(Book book, User user, String content, boolean isAnonymous);

    boolean rateComment(int commentId, int rateChange);

    boolean isBookCodeAvailable(String bookCode);

    boolean deleteBookCopy(BookCopy bookCopy);

    PageList<Book> listBookByPage(int page);

    BookComment getBookComment(int commentId);


    boolean deleteComment(int commentId);
}
