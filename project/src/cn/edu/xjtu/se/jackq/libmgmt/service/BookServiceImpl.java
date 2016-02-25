package cn.edu.xjtu.se.jackq.libmgmt.service;


import cn.edu.xjtu.se.jackq.libmgmt.dao.BookDao;
import cn.edu.xjtu.se.jackq.libmgmt.dao.BookLoanDao;
import cn.edu.xjtu.se.jackq.libmgmt.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;


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
    public List<Book> findBook(String query) {
        return null;
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
    public List<BookLoan> listLoanBook(User user) {
        return bookLoanDao.listLoanByUser(user, 1);
    }
}
