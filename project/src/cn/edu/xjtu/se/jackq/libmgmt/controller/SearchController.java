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

    /**
     * Search books satisfying certain criteria and return result in Ajax
     * <br />
     * The matching strategy is merely literal comparison, which means only a field
     * in database which <em>full</em> contains the query string can be selected to
     * search result.
     * <br />
     * The {@code %} mark is used here as a wildcard.
     * <br />
     * There are no matching methods selected by default. So, to get result,
     * at least set parameter {@code byName} to true or 1
     *
     * @param query    is the keyword to be used to search for books
     * @param byCode   indicates whether match query in {@link Book#bookCode BookCode field}
     * @param byName   indicates whether match query in {@link Book#bookName BookName field}
     * @param byAuthor indicates whether match query in {@link Book#author Author field}
     * @return JSON encoded book list
     */
    @RequestMapping(value = "ajax", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String searchAjax(@RequestParam("q") String query,
                             @RequestParam("byCode") boolean byCode,
                             @RequestParam("byName") boolean byName,
                             @RequestParam("byAuthor") boolean byAuthor) {
        // Get book list satisfying the requested criteria form book service
        List<Book> books = bookService.searchBook(query, byCode, byName, byAuthor);

        // Encode and return search result in JSON
        return encodeJsonForSearch(books, true);
    }

    /**
     * Search books satisfying certain criteria which can be lend to student,
     * and then return result in Ajax.
     * <p>
     * The matching strategy of the method is the same as
     * {@link #searchAjax(String, boolean, boolean, boolean) searchAjax method}
     * while it also filters the result based on certain user.
     * Only those books which have at least one copy in shelf and no other copy
     * lent to current user can be listed in final result.
     * <p>
     * This operation is only accessible to librarian.
     * (declared by {@link Auth Auth anotation}
     *
     * @param query    is the keyword to be used to search for books
     * @param byCode   indicates whether match query in {@link Book#bookCode BookCode field}
     * @param byName   indicates whether match query in {@link Book#bookName BookName field}
     * @param byAuthor indicates whether match query in {@link Book#author Author field}
     * @param userId   current user to whom books will be lend
     * @return JSON encoded book list
     */
    @RequestMapping(value = "loanAjax", produces = "application/json; charset=utf-8")
    @ResponseBody
    @Auth(userRoles = UserRole.LIBRARIAN)
    public String searchToLendAjax(@RequestParam("q") String query,
                                   @RequestParam("byCode") boolean byCode,
                                   @RequestParam("byName") boolean byName,
                                   @RequestParam("byAuthor") boolean byAuthor,
                                   @RequestParam("userId") int userId) {
        // Validate the user id
        User user = userService.getUser(userId);
        if (user == null) {
            return encodeJsonForSearch(null, false);
        }

        // Get book list satisfying the requested criteria form book service
        List<Book> books = bookService.searchBookToLend(user, query, byCode, byName, byAuthor);

        // Encode and return search result in JSON
        return encodeJsonForSearch(books, true);
    }


    /**
     * Encode {@link Book Book} list to JSON text
     *
     * @param bookList the book list which will be encoded
     * @param status   indicates whether the response is valid
     * @return JSON text of encoded book list
     */
    private String encodeJsonForSearch(List<Book> bookList, boolean status) {
        StringBuilder stringBuilder = new StringBuilder();
        // Prepend Status
        stringBuilder.append("{\"success\":");
        stringBuilder.append(status);
        stringBuilder.append(",\"data\": [");
        // only try to access bookList when status is true
        // in other scenarios when status is false, the book list will be ignored
        if (status) {
            for (Book book : bookList) {
                // Start Object
                stringBuilder.append("{");
                // Book id (JSON number value)
                stringBuilder.append("\"id\": ");
                stringBuilder.append(book.getId());
                stringBuilder.append(", ");
                // Book book code (JSON string value)
                stringBuilder.append("\"bookcode\": \"");
                stringBuilder.append(book.getBookCode());
                stringBuilder.append("\", ");
                // Book author (JSON string value)
                stringBuilder.append("\"author\": \"");
                stringBuilder.append(book.getAuthor());
                stringBuilder.append("\", ");
                // Book year (JSON string value)
                stringBuilder.append("\"year\": ");
                stringBuilder.append(book.getYearOfPublish());
                stringBuilder.append(", ");
                // Book book name (JSON string value)
                stringBuilder.append("\"bookname\": \"");
                stringBuilder.append(book.getBookName());
                stringBuilder.append("\", ");
                // Book ISBN (JSON string value)
                stringBuilder.append("\"isbn\": \"");
                stringBuilder.append(book.getIsbn());
                stringBuilder.append("\"");
                // End Object
                stringBuilder.append("},");
            }
            if (stringBuilder.lastIndexOf(",") == stringBuilder.length() - 1) {
                // Remove extra comma appended by last object in JSON array
                stringBuilder.delete(stringBuilder.lastIndexOf(","), stringBuilder.lastIndexOf(",") + 1);
            }
        }
        // Close JSON Data
        stringBuilder.append("]}");
        // Return String Value
        return stringBuilder.toString();
    }

}
