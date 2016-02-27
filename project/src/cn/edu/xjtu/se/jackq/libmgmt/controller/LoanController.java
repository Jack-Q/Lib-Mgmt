package cn.edu.xjtu.se.jackq.libmgmt.controller;

import cn.edu.xjtu.se.jackq.libmgmt.annotation.Auth;
import cn.edu.xjtu.se.jackq.libmgmt.entity.Book;
import cn.edu.xjtu.se.jackq.libmgmt.entity.BookLoan;
import cn.edu.xjtu.se.jackq.libmgmt.entity.User;
import cn.edu.xjtu.se.jackq.libmgmt.entity.UserRole;
import cn.edu.xjtu.se.jackq.libmgmt.service.BookService;
import cn.edu.xjtu.se.jackq.libmgmt.service.UserService;
import cn.edu.xjtu.se.jackq.libmgmt.session.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@Auth(userRoles = UserRole.LIBRARIAN)
@RequestMapping("/loan")
public class LoanController {
    private static final int LOAN_PERIOD = 30;
    private static final int BOOK_LIMIT = 10;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @RequestMapping("lend")
    public String lendPrep(Model model) {
        model.addAttribute("UserPageTitle", "Lend books");
        model.addAttribute("NextPage", "/loan/lend/");
        return "loan/user";
    }

    @RequestMapping("lend/{UserId}")
    public String lend(Model model, @PathVariable("UserId") int userId) {
        User user = userService.getUser(userId);
        // Only Students Can borrow books
        if (user == null || !user.getRoles().contains(UserRole.STUDENT)) {
            return "redirect:/loan/lend";
        }
        int bookHoldingNum = bookService.listLoanBook(user, BookService.LIST_LOAN_CURR).size();
        model.addAttribute("CurrentUser", user);
        model.addAttribute("BookHoldingNum", bookHoldingNum);
        model.addAttribute("BookLeavingNum", BOOK_LIMIT - bookHoldingNum);
        return "loan/lend";
    }

    @RequestMapping(value = "lendAjax", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String doLend(@RequestParam("bookId") int bookId, @RequestParam("userId") int userId) {
        String errorResponse = "{\"success\": false}";
        User user = userService.getUser(userId);
        Book book = bookService.getBook(bookId);
        if (user == null || book == null) {
            return errorResponse;
        }
        if (!bookService.lendBook(user, book, LOAN_PERIOD)) {
            return errorResponse;
        }
        return "{\"success\": true, \"period\": " + LOAN_PERIOD + "}";
    }

    @RequestMapping("return") // Since return is a keyword in Java, this method use returnBook as its name instead
    public String returnBookPrep(Model model) {
        model.addAttribute("UserPageTitle", "Return books");
        model.addAttribute("NextPage", "/loan/return/");
        return "loan/user";
    }


    @RequestMapping("return/{UserId}")
    public String returnBook(Model model, @PathVariable("UserId") int userId) {
        User user = userService.getUser(userId);
        // Only Students Can borrow books
        if (user == null || !user.getRoles().contains(UserRole.STUDENT)) {
            return "redirect:/loan/return";
        }
        List<BookLoan> bookLoanList = bookService.listLoanBook(user, BookService.LIST_LOAN_CURR);
        model.addAttribute("CurrentUser", user);
        model.addAttribute("LoanList", bookLoanList);
        model.addAttribute("BookHoldingNum", bookLoanList.size());
        model.addAttribute("BookLeavingNum", BOOK_LIMIT - bookLoanList.size());
        model.addAttribute("DateTimeNow", new Date());  // Use current time to compare the deadline
        return "loan/return";
    }

    @RequestMapping(value = "returnAjax", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public String doReturnBook(@RequestParam("id") int loanId, @RequestParam("action") String action) {
        boolean result = false;
        String response = "{\"success\": true}";
        switch (action) {
            case "return":
                result = bookService.returnBook(loanId);
                break;
            case "returnFined":
                result = bookService.returnBookFined(loanId);
                break;
            case "broken":
                result = bookService.returnBookBroken(loanId);
                break;
            case "extend":
                result = bookService.extendBookLoan(loanId, LOAN_PERIOD);
                break;
            case "lost":
                result = bookService.returnBookLost(loanId);
                break;
        }
        if (!result) {
            return "{\"success\": false}";
        }
        return response;
    }


    @RequestMapping("status")
    @Auth(userRoles = UserRole.STUDENT)
    public String status(HttpSession httpSession, Model model) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("Auth");
        int userId = sessionUser.getId();

        User user = userService.getUser(userId);

        model.addAttribute("DateTimeNow", new Date());  // Use current time to compare the deadline
        model.addAttribute("CurrentLoanList", bookService.listLoanBook(user, BookService.LIST_LOAN_CURR));
        model.addAttribute("FinishedLoanList", bookService.listLoanBook(user, BookService.LIST_LOAN_FINISH));

        return "loan/status";
    }

    @RequestMapping("status/{userId}")
    @Auth(userRoles = {UserRole.ADMIN, UserRole.LIBRARIAN})
    public String statusUser(@PathVariable("userId") int userId, Model model) {

        User user = userService.getUser(userId);

        model.addAttribute("DateTimeNow", new Date());  // Use current time to compare the deadline
        model.addAttribute("CurrentUser", user);
        model.addAttribute("CurrentLoanList", bookService.listLoanBook(user, BookService.LIST_LOAN_CURR));
        model.addAttribute("FinishedLoanList", bookService.listLoanBook(user, BookService.LIST_LOAN_FINISH));
        model.addAttribute("ManageView", true);
        return "loan/status";
    }
}
