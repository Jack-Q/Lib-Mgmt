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
    public BookCopy getBookCopy(int id) {
        return bookDao.getBookCopy(id);
    }


    @Override
    public Book addBook(Book book) {
        return bookDao.addBook(book);
    }

    public void addBookAndCopy(String name, String code, String author, String isbn, String publisher, Integer yearOfPublish, String description, Integer copy) {
        Book book = new Book();
        book.setAuthor(author);
        book.setBookName(name);
        book.setBookCode(code);
        book.setIsbn(isbn);
        book.setPublisher(publisher);
        if (yearOfPublish != null) {
            book.setYearOfPublish(yearOfPublish);
        }
        book.setDescription(description);
        addBook(book);
        addBookCopies(book.getId(), copy == null || copy <= 0 ? 3 : copy);
    }

    @Override
    public boolean updateBook(Book book) {
        bookDao.updateBook(book);
        return true;
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
        return true;
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
        bookLoan.setLoanPeriod(period);

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

    private boolean returnBook(int loanId, boolean isLost, boolean isBroken, boolean isFined) {
        BookLoan bookLoan = bookLoanDao.getLoan(loanId);
        if (bookLoan == null || bookLoan.isFinished()) {
            return false;
        }
        BookCopy bookCopy = bookLoan.getBookCopy();
        bookCopy.setNote(isLost ? "Lost" : isBroken ? "Broken" : "");
        bookCopy.setLoanable(!isLost);
        bookCopy.setStatus(isLost ? BookCopyStatus.UNAVAILABLE : BookCopyStatus.ON_SHELF);

        Date returnDate = new Date();
        boolean isRequireFined = false;
        double finedAmount = 0;
        if (isFined) {
            if (bookLoan.getDeadlineOfReturning().before(returnDate)) {
                isRequireFined = true;
                finedAmount = bookLoan.calcLoanFinedAmount(returnDate);
            }
        }

        bookLoan.setNote(isLost ? "Book Lost" : isBroken ? "Book Broken" : isFined && isRequireFined ? "Fined" : "");
        bookLoan.setDateOfReturning(returnDate);
        bookLoan.setFinedAmount(finedAmount);
        bookLoan.setFinished(true);

        bookDao.updateBookCopy(bookCopy);
        bookLoanDao.updateLoan(bookLoan);
        return true;
    }


    @Override
    public boolean returnBook(int loanId) {
        return returnBook(loanId, false, false, true);
    }

    @Override
    public boolean returnBookFined(int loanId) {
        return returnBook(loanId, false, false, true);

    }

    @Override
    public boolean returnBookBroken(int loanId) {
        return returnBook(loanId, false, true, false);

    }

    @Override
    public boolean returnBookLost(int loanId) {
        return returnBook(loanId, true, false, false);

    }

    @Override
    public boolean extendBookLoan(int loanId, int loanPeriod) {

        BookLoan bookLoan = bookLoanDao.getLoan(loanId);
        if (bookLoan == null || bookLoan.isFinished()) {
            return false;
        }

        Calendar instance = Calendar.getInstance();
        instance.setTime(bookLoan.getDeadlineOfReturning());
        instance.add(Calendar.DAY_OF_MONTH, loanPeriod);
        bookLoan.setDeadlineOfReturning(instance.getTime());
        bookLoan.setLoanPeriod(bookLoan.getLoanPeriod() + loanPeriod);
        bookLoan.setNote("Loan Extended");
        BookCopy bookCopy = bookLoan.getBookCopy();
        bookCopy.setNote("Return @ " + instance.getTime().toString());

        bookDao.updateBookCopy(bookCopy);
        bookLoanDao.updateLoan(bookLoan);
        return true;
    }

    @Override
    public void commentBook(Book book, User user, String content) {

        BookComment bookComment = new BookComment();
        bookComment.setBook(book);
        bookComment.setUser(user);
        bookComment.setContent(content);
        bookComment.setDateOfComment(new Date());
        bookDao.addComment(bookComment);
    }

    @Override
    public boolean rateComment(int commentId, int rateChange) {
        if (rateChange != 1 && rateChange != -1) {
            return false;
        }
        BookComment bookComment = bookDao.getComment(commentId);
        if (bookComment == null) {
            return false;
        }
        bookComment.setStars(bookComment.getStars() + rateChange);
        return bookDao.updateBookComment(bookComment);
    }

    @Override
    public boolean deleteBookCopy(BookCopy bookCopy) {
        bookCopy.setStatus(BookCopyStatus.UNAVAILABLE);
        bookCopy.setNote("The copy is deleted");
        bookCopy.setLoanable(false);

        bookDao.updateBookCopy(bookCopy);
        return true;
    }

    @Override
    public boolean isBookCodeAvailable(String bookCode) {
        return bookDao.isBookCodeAvailable(bookCode);
    }

}
