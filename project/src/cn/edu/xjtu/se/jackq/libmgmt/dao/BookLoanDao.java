package cn.edu.xjtu.se.jackq.libmgmt.dao;

import cn.edu.xjtu.se.jackq.libmgmt.entity.BookLoan;
import cn.edu.xjtu.se.jackq.libmgmt.entity.User;

import java.util.List;

/**
 * Created by Jack on 2/14/2016.
 */
public interface BookLoanDao {
    BookLoan add(BookLoan bookLoan);

    List<BookLoan> listLoanByUser(User user, int listPolicy);


}
