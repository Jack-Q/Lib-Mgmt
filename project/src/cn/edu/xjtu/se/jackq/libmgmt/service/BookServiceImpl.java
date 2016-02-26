package cn.edu.xjtu.se.jackq.libmgmt.service;


import cn.edu.xjtu.se.jackq.libmgmt.dao.BookDao;
import cn.edu.xjtu.se.jackq.libmgmt.dao.BookLoanDao;
import cn.edu.xjtu.se.jackq.libmgmt.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;

    @Autowired
    private BookLoanDao bookLoanDao;

    @Override
    public Book getBook(int id) {
        return bookDao.getBookById(id);
    }

    @Override
    public Book getBook(String bookCode) {
        return bookDao.getBookByBookCode(bookCode);
    }

    @Override
    public Book addBook(Book book) {
        return bookDao.addBook(book);
    }

    @Override
    public Book updateBook(Book book) {
        return bookDao.updateBook(book);
    }

    @Override
    public boolean addBookCopies(int bookId, int count) {
        Book book = bookDao.getBookById(bookId);
        if (book == null) {
            return false;
        }

        Date date = new Date();
        for (int i = 0; i < count; i++) {
            BookCopy bookCopy = new BookCopy();
            bookCopy.setBook(book);
            bookCopy.setStatus(BookCopyStatus.ON_SHELF);
            bookCopy.setLoanable(true);
            bookCopy.setDateOfRecord(date);
            if (!bookDao.AddCopy(bookCopy)) {
                return false;
            }
        }
        return false;
    }

    @Override
    public List<Book> listBook() {
        return bookDao.listBook();
    }

    @Override
    public List<Book> searchBook(String query) {
        return searchBook(query, true, true, true);
    }

    @Override
    public boolean lendBook(User user, Book book, int period) {

        Set<BookCopy> bookCopies = book.getBookCopies();
        BookCopy bookCopyToLend = null;
        for (BookCopy bookCopy : bookCopies) {
            if (bookCopy.getStatus() == BookCopyStatus.ON_SHELF) {
                bookCopyToLend = bookCopy;
                break;
            }
        }
        if (bookCopyToLend == null) {
            return false;
        }
        BookLoan bookLoan = new BookLoan();
        Calendar instance = Calendar.getInstance();

        bookLoan.setBookCopy(bookCopyToLend);
        bookLoan.setDateOfBorrowing(instance.getTime());
        instance.add(Calendar.DAY_OF_MONTH, period);
        bookLoan.setDeadlineOfReturning(instance.getTime());
        bookLoan.setUser(user);

        bookCopyToLend.setStatus(BookCopyStatus.AWAY);
        bookCopyToLend.setLoanable(false);
        bookCopyToLend.setNote("Return @ " + instance.getTime().toString());

        bookDao.updateBookCopy(bookCopyToLend);
        bookLoanDao.add(bookLoan);
        return true;
    }

    @Override
    public List<BookLoan> listLoanBook(User user, int listPolicy) {
        List<BookLoan> bookLoans;
        switch (listPolicy) {
            case LIST_LOAN_CURR:
                bookLoans = bookLoanDao.listLoanByUser(user, BookLoanDao.LIST_LOAN_CURR);
                break;
            case LIST_LOAN_FINISH:
                bookLoans = bookLoanDao.listLoanByUser(user, BookLoanDao.LIST_LOAN_FINISH);
                break;
            case LIST_LOAN_ALL:
            default:
                bookLoans = bookLoanDao.listLoanByUser(user, BookLoanDao.LIST_LOAN_ALL);
                break;
        }
        return bookLoans;
    }

    @Override
    public List<Book> searchBook(String query, boolean byCode, boolean byName, boolean byAuthor) {
        if (!(byCode || byAuthor || byName)) {
            return new ArrayList<>();
        }
        return bookDao.searchBook(query, byCode, byName, byAuthor);
    }

    @Override
    public List<Book> searchBookToLend(User user, String query, boolean byCode, boolean byName, boolean byAuthor) {
        if (!(byCode || byAuthor || byName)) {
            return new ArrayList<>();
        }
        return bookLoanDao.searchBookToLend(user, query, byCode, byName, byAuthor);
    }

}
