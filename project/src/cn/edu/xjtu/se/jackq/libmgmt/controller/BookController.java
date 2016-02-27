package cn.edu.xjtu.se.jackq.libmgmt.controller;

import cn.edu.xjtu.se.jackq.libmgmt.annotation.Auth;
import cn.edu.xjtu.se.jackq.libmgmt.annotation.PartialView;
import cn.edu.xjtu.se.jackq.libmgmt.entity.*;
import cn.edu.xjtu.se.jackq.libmgmt.service.BookService;
import cn.edu.xjtu.se.jackq.libmgmt.service.UserService;
import cn.edu.xjtu.se.jackq.libmgmt.session.SessionUser;
import cn.edu.xjtu.se.jackq.libmgmt.viewmodel.BookAdd;
import cn.edu.xjtu.se.jackq.libmgmt.viewmodel.BookEdit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/book/")
public class BookController {
    @Autowired
    BookService bookService;

    @Autowired
    UserService userService;


    @Auth(userRoles = {UserRole.ADMIN, UserRole.LIBRARIAN})
    @RequestMapping("manage")
    public String manage(Model model) {
        return this.manage(1, model);
    }

    @Auth(userRoles = {UserRole.ADMIN, UserRole.LIBRARIAN})
    @RequestMapping("manage/{page}")
    public String manage(@PathVariable(value = "page") int page, Model model) {
        List<Book> bookList = bookService.listBook();
        model.addAttribute("BookList", bookList);
        return "book/manage";
    }

    @RequestMapping("copiesPartial/{id}")
    @PartialView
    public String copiesPartial(@PathVariable int id, Model model) {
        Book book = bookService.getBook(id);
        model.addAttribute("CurrentBook", book);
        Set<BookCopy> bookCopiesSet = book.getBookCopies();
        List<BookCopy> bookCopies = new ArrayList<>(bookCopiesSet);
        bookCopies.sort((a, b) -> a.getId() - b.getId());
        model.addAttribute("BookCopies", bookCopies);
        return "book/copiesPartial";
    }

    @RequestMapping("commentPartial/{id}")
    @PartialView
    public String commentPartial(@PathVariable int id, Model model) {
        Book book = bookService.getBook(id);

        model.addAttribute("CurrentBook", book);
        Set<BookComment> bookCommentSet = book.getBookComments();
        ArrayList<BookComment> bookCommentList = new ArrayList<>(bookCommentSet);
        bookCommentList.sort((a, b) -> a.getStars() - b.getStars() == 0 ?
                (int) (a.getDateOfComment().getTime() / 10000 - b.getDateOfComment().getTime() / 10000)
                : b.getStars() - a.getStars());
        model.addAttribute("BookComments", bookCommentList);
        return "book/commentPartial";
    }

    @Auth
    @RequestMapping(value = "comment/{BookId}", method = RequestMethod.POST)
    public String doComment(@PathVariable("BookId") int bookId,
                            @RequestParam("content") String content,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("Auth");
        User user = userService.getUser(sessionUser.getId());
        Book book = bookService.getBook(bookId);
        if (user != null && book != null) {
            bookService.commentBook(book, user, content);
            redirectAttributes.addFlashAttribute("indexMessageId", "book.comment.success");
        } else {
            redirectAttributes.addFlashAttribute("indexMessageId", "book.comment.failed");
        }
        return "redirect:/book/detail/" + bookId;
    }

    @Auth
    @ResponseBody
    @RequestMapping(value = "commentRateAjax", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public String doCommentRate(@RequestParam("id") int commentId, @RequestParam("action") String action) {
        boolean result = false;

        switch (action) {
            case "up":
                result = bookService.rateComment(commentId, 1);
                break;
            case "down":
                result = bookService.rateComment(commentId, -1);
                break;
        }
        return result ?
                "{\"success\":true}" : "{\"success\":false}";
    }

    @RequestMapping("detail/{id}")
    public String detail(@PathVariable("id") int id, Model model) {
        Book book = bookService.getBook(id);
        if (book == null) {
            return "redirect:/error/argument";
        }
        model.addAttribute("CurrentBook", book);
        return "book/detail";
    }

    @Auth(userRoles = {UserRole.ADMIN, UserRole.LIBRARIAN})
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(@ModelAttribute("BookAdd") BookAdd bookAdd) {
        return "book/add";
    }


    @Auth(userRoles = {UserRole.ADMIN, UserRole.LIBRARIAN})
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String doAdd(@ModelAttribute("BookAdd") BookAdd bookAdd,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        if (bookAdd == null) {
            return "book/Add";
        }

        String bookCode = bookAdd.getBookCode();
        if (bookCode == null) {
            model.addAttribute("errorMessageId", "book.add.error.bookCode");
            return "book/add";
        }

        if (bookAdd.getBookName() == null) {
            model.addAttribute("errorMessageId", "book.add.error.bookName");
            return "book/add";
        }

        String isbn = bookAdd.getIsbn();

        Pattern pattern = Pattern.compile("\\d{10}(\\d{3})?");
        if (!pattern.matcher(isbn).matches()) {
            model.addAttribute("errorMessageId", "book.add.error.isbn");
            return "book/add";
        }

        if (!bookService.isBookCodeAvailable(bookAdd.getBookCode())) {
            model.addAttribute("errorMessageId", "book.add.error.bookCodeConflict");
            return "book/add";
        }

        Book book = new Book();
        book.setBookName(bookAdd.getBookName());
        book.setBookCode(bookAdd.getBookCode());
        book.setIsbn(bookAdd.getIsbn());
        book.setPublisher(bookAdd.getPublisher());
        book.setBookNote(bookAdd.getBookNote());
        book.setDescription(bookAdd.getDescription());
        book.setYearOfPublish(bookAdd.getYearOfPublish());
        book.setAuthor(bookAdd.getAuthor());

        bookService.addBook(book);
        if (book.getId() == 0) {
            model.addAttribute("errorMessageId", "book.add.error.failed");
            return "book/add";
        }

        bookService.addBookCopies(book.getId(), bookAdd.getCount());
        redirectAttributes.addFlashAttribute("indexMessageId", "book.add.success");
        return "redirect:/book/manage";
    }


    @RequestMapping(value = "edit/{BookId}", method = RequestMethod.GET)
    @Auth(userRoles = {UserRole.ADMIN, UserRole.LIBRARIAN})
    public String edit(@PathVariable("BookId") int bookId,
                       @ModelAttribute("BookEdit") BookEdit bookEdit,
                       Model model) {
        Book book = bookService.getBook(bookId);
        if (book == null) {
            return "redirect:/error/argument";
        }

        bookEdit.setBookName(book.getBookName());
        bookEdit.setAuthor(book.getAuthor());
        bookEdit.setBookCode(book.getBookCode());
        bookEdit.setIsbn(book.getIsbn());
        bookEdit.setPublisher(book.getPublisher());
        bookEdit.setYearOfPublish(book.getYearOfPublish());
        bookEdit.setBookNote(book.getBookNote());
        bookEdit.setDescription(book.getDescription());


        model.addAttribute("CurrentBook", book);
        return "book/edit";
    }


    @RequestMapping(value = "edit/{BookId}", method = RequestMethod.POST)
    @Auth(userRoles = {UserRole.ADMIN, UserRole.LIBRARIAN})
    public String doEdit(@PathVariable("BookId") int bookId,
                         @ModelAttribute("BookEdit") BookEdit bookEdit,
                         HttpSession httpSession,
                         Model model,
                         RedirectAttributes redirectAttributes) {

        Book book = bookService.getBook(bookId);
        if (book == null) {
            return "redirect:error/argument";
        }

        model.addAttribute("CurrentBook", book);

        book.setBookName(bookEdit.getBookName());
        book.setAuthor(bookEdit.getAuthor());
        book.setBookCode(bookEdit.getBookCode());
        book.setIsbn(bookEdit.getIsbn());
        book.setPublisher(bookEdit.getPublisher());
        book.setYearOfPublish(bookEdit.getYearOfPublish());
        book.setBookNote(bookEdit.getBookNote());
        book.setDescription(bookEdit.getDescription());


        if (!bookService.updateBook(book)) {
            model.addAttribute("errorMessageId", "book.edit.error.failed");
            return "book/edit";
        }
        redirectAttributes.addFlashAttribute("indexMessageId", "book.edit.success");
        return "redirect:/book/manage";
    }

    @RequestMapping(value = "copies/{BookId}")
    @Auth(userRoles = {UserRole.ADMIN, UserRole.LIBRARIAN})
    public String copies(@PathVariable("BookId") int bookId,
                         HttpSession httpSession,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        Book book = bookService.getBook(bookId);
        if (book == null) {
            return "redirect:error/argument";
        }

        model.addAttribute("CurrentBook", book);
        model.addAttribute("CurrentBookCopyList", book.getBookCopies());

        return "book/copies";
    }

    @RequestMapping(value = "deleteCopy/{BookCopyId}")
    @Auth(userRoles = {UserRole.ADMIN, UserRole.LIBRARIAN})
    public String deleteCopy(@PathVariable("BookCopyId") int bookCopyId,
                             RedirectAttributes redirectAttributes) {
        BookCopy bookCopy = bookService.getBookCopy(bookCopyId);
        if (bookCopy == null) {
            return "redirect:error/argument";
        }

        if (bookCopy.getStatus() == BookCopyStatus.AWAY) {
            redirectAttributes.addFlashAttribute("indexMessageId", "book.manageCopy.delete.failed");
            return "redirect:/book/copies/" + bookCopy.getBook().getId();
        }

        boolean result = bookService.deleteBookCopy(bookCopy);

        if (result) {
            redirectAttributes.addFlashAttribute("indexMessageId", "book.manageCopy.delete.success");
        } else {
            redirectAttributes.addFlashAttribute("indexMessageId", "book.manageCopy.delete.failed");
        }
        return "redirect:/book/copies/" + bookCopy.getBook().getId();
    }

    @RequestMapping(value = "addCopies")
    @Auth(userRoles = {UserRole.ADMIN, UserRole.LIBRARIAN})
    public String addCopies(@RequestParam("bookId") int bookId,
                            @RequestParam("numOfNewCopies") int numOfNewCopies,
                            RedirectAttributes redirectAttributes) {
        Book book = bookService.getBook(bookId);
        if (book == null) {
            return "redirect:error/argument";
        }

        boolean result = bookService.addBookCopies(bookId, numOfNewCopies);

        if (result) {
            redirectAttributes.addFlashAttribute("indexMessageId", "book.manageCopy.add.success");
        } else {
            redirectAttributes.addFlashAttribute("indexMessageId", "book.manageCopy.add.failed");
        }
        return "redirect:/book/copies/" + bookId;
    }

}
