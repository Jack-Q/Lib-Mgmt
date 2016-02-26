package cn.edu.xjtu.se.jackq.libmgmt.controller;

import cn.edu.xjtu.se.jackq.libmgmt.annotation.Auth;
import cn.edu.xjtu.se.jackq.libmgmt.annotation.PartialView;
import cn.edu.xjtu.se.jackq.libmgmt.entity.Book;
import cn.edu.xjtu.se.jackq.libmgmt.entity.User;
import cn.edu.xjtu.se.jackq.libmgmt.entity.UserRole;
import cn.edu.xjtu.se.jackq.libmgmt.service.BookService;
import cn.edu.xjtu.se.jackq.libmgmt.session.SessionUser;
import cn.edu.xjtu.se.jackq.libmgmt.viewmodel.BookAdd;
import cn.edu.xjtu.se.jackq.libmgmt.viewmodel.BookEdit;
import cn.edu.xjtu.se.jackq.libmgmt.viewmodel.UserEdit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/book/")
public class BookController {
    @Autowired
    BookService bookService;

    @RequestMapping({"", "index"})
    public String index() {
        return "book/index";
    }

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
        model.addAttribute("BookCopies", book.getBookCopies());
        return "book/copiesPartial";
    }

    @RequestMapping("commentPartial/{id}")
    @PartialView
    public String commentPartial(@PathVariable int id, Model model) {
        Book book = bookService.getBook(id);

        model.addAttribute("CurrentBook", book);
        model.addAttribute("BookComments", book.getBookComments());
        return "book/commentPartial";
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

        if (null != bookService.getBook(bookCode)) {
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
//        User user = userService.getUser(userId);
//        if (user == null || user.getRoles().contains(UserRole.ADMIN)) {
//            return "redirect:/error/argument";
//        }
//
//        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("Auth");
//
//        // Librarian cannot change his password
//        if (user.getRoles().contains(UserRole.LIBRARIAN) && !sessionUser.getRoles().contains(UserRole.ADMIN)) {
//            return "redirect:/error/denied";
//        }


        userEdit.setName(user.getName());
        userEdit.setEmail(user.getEmail());
        userEdit.setPhoneNumber(user.getPhoneNumber());
        userEdit.setDateOfBirth(user.getDateOfBirth());

        bookEdit.setName(book.getName());


        model.addAttribute("CurrentUser", user);
        return "user/edit";
    }


    @Auth(userRoles = {UserRole.ADMIN, UserRole.LIBRARIAN})
    @RequestMapping(value = "edit/{BookId}", method = RequestMethod.POST)
    public String doEdit(@PathVariable("UserId") int userId,
                         @ModelAttribute("UserEdit") UserEdit userEdit,
                         HttpSession httpSession,
                         Model model,
                         RedirectAttributes redirectAttributes) {

        User user = userService.getUser(userId);
        if (user == null || user.getRoles().contains(UserRole.ADMIN)) {
            return "redirect:error/argument";
        }

        model.addAttribute("CurrentUser", user);

        if (userEdit.getName() == null) {
            model.addAttribute("errorMessageId", "user.edit.error.name");

            return "user/edit";
        }


        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("Auth");

        // Librarian cannot change his password
        if (user.getRoles().contains(UserRole.LIBRARIAN) && !sessionUser.getRoles().contains(UserRole.ADMIN)) {
            return "redirect:/error/denied";
        }

        user.setName(userEdit.getName());
        user.setEmail(userEdit.getEmail());
        user.setDateOfBirth(userEdit.getDateOfBirth());
        user.setPhoneNumber(userEdit.getPhoneNumber());

        if (!userService.updateUser(user)) {
            model.addAttribute("errorMessageId", "user.edit.error.failed");
            return "user/edit";
        }
        redirectAttributes.addFlashAttribute("indexMessageId", "user.edit.success");
        return "redirect:/user/manage";
    }

}
