package cn.edu.xjtu.se.jackq.libmgmt.service;

import cn.edu.xjtu.se.jackq.libmgmt.dao.BookCopyDao;
import cn.edu.xjtu.se.jackq.libmgmt.dao.BookDao;
import cn.edu.xjtu.se.jackq.libmgmt.entity.Book;
import cn.edu.xjtu.se.jackq.libmgmt.entity.BookCopy;
import cn.edu.xjtu.se.jackq.libmgmt.entity.BookCopyStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;

    @Autowired
    private BookCopyDao bookCopyDao;

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
}
