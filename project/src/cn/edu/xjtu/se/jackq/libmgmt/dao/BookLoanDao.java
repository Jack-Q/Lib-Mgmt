package cn.edu.xjtu.se.jackq.libmgmt.dao;

import cn.edu.xjtu.se.jackq.libmgmt.entity.Book;
import cn.edu.xjtu.se.jackq.libmgmt.entity.BookLoan;
import cn.edu.xjtu.se.jackq.libmgmt.entity.User;

import java.util.List;

public interface BookLoanDao {
    int LIST_LOAN_ALL = 1;
    int LIST_LOAN_CURR = 2;
    int LIST_LOAN_FINISH = 3;

    BookLoan add(BookLoan bookLoan);

    List<BookLoan> listLoanByUser(User user, int listPolicy);

    List<Book> searchBookToLend(User user, String query, boolean byCode, boolean byName, boolean byAuthor);

    BookLoan getLoan(int loanId);

    boolean updateLoan(BookLoan bookLoan);
}
