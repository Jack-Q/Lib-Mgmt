package cn.edu.xjtu.se.jackq.libmgmt.controller;

import cn.edu.xjtu.se.jackq.libmgmt.annotation.Auth;
import cn.edu.xjtu.se.jackq.libmgmt.entity.Book;
import cn.edu.xjtu.se.jackq.libmgmt.entity.User;
import cn.edu.xjtu.se.jackq.libmgmt.entity.UserRole;
import cn.edu.xjtu.se.jackq.libmgmt.service.BookService;
import cn.edu.xjtu.se.jackq.libmgmt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {
    @Autowired
    BookService bookService;

    @Autowired
    UserService userService;

    @RequestMapping("")
    public String search(@RequestParam("q") String query, Model model) {
        List<Book> bookList = bookService.searchBook(query);
        model.addAttribute("QueryString", query);
        model.addAttribute("ResultList", bookList);
        return "search/result";
    }

    @RequestMapping(value = "ajax", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String searchAjax(@RequestParam("q") String query,
                             @RequestParam("byCode") boolean byCode,
                             @RequestParam("byName") boolean byName,
                             @RequestParam("byAuthor") boolean byAuthor) {
        List<Book> books = bookService.searchBook(query, byCode, byName, byAuthor);
        return encodeJsonForSearch(books, true);
    }

    @RequestMapping(value = "loanAjax", produces = "application/json; charset=utf-8")
    @ResponseBody
    @Auth(userRoles = UserRole.LIBRARIAN)
    public String searchToLendAjax(@RequestParam("q") String query,
                                   @RequestParam("byCode") boolean byCode,
                                   @RequestParam("byName") boolean byName,
                                   @RequestParam("byAuthor") boolean byAuthor,
                                   @RequestParam("userId") int userId) {
        User user = userService.getUser(userId);
        if (user == null) {
            return encodeJsonForSearch(null, false);
        }
        List<Book> books = bookService.searchBookToLend(user, query, byCode, byName, byAuthor);
        return encodeJsonForSearch(books, true);
    }


    private String encodeJsonForSearch(List<Book> bookList, boolean status) {
        StringBuilder stringBuilder = new StringBuilder();
        // Prepend Status
        stringBuilder.append("{\"success\":");
        stringBuilder.append(status);
        stringBuilder.append(",\"data\": [");
        // Close JSON Data
        if (status) {
            for (Book book : bookList) {
                // Start Object
                stringBuilder.append("{");
                // Book id
                stringBuilder.append("\"id\": ");
                stringBuilder.append(book.getId());
                stringBuilder.append(", ");
                // Book book code
                stringBuilder.append("\"bookcode\": \"");
                stringBuilder.append(book.getBookCode());
                stringBuilder.append("\", ");

                // Book author
                stringBuilder.append("\"author\": \"");
                stringBuilder.append(book.getAuthor());
                stringBuilder.append("\", ");
                // Book year
                stringBuilder.append("\"year\": ");
                stringBuilder.append(book.getYearOfPublish());
                stringBuilder.append(", ");
                // Book book name
                stringBuilder.append("\"bookname\": \"");
                stringBuilder.append(book.getBookName());
                stringBuilder.append("\", ");
                // Book ISBN
                stringBuilder.append("\"isbn\": \"");
                stringBuilder.append(book.getIsbn());
                stringBuilder.append("\"");
                // End Object
                stringBuilder.append("},");
            }
            if (stringBuilder.lastIndexOf(",") == stringBuilder.length() - 1) {
                stringBuilder.delete(stringBuilder.lastIndexOf(","), stringBuilder.lastIndexOf(",") + 1); // Remove extra comma
            }
        }
        // Return String Value
        stringBuilder.append("]}");
        return stringBuilder.toString();
    }

}
