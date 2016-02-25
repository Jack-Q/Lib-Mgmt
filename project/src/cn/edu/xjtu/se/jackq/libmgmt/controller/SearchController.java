package cn.edu.xjtu.se.jackq.libmgmt.controller;

import cn.edu.xjtu.se.jackq.libmgmt.entity.Book;
import cn.edu.xjtu.se.jackq.libmgmt.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {
    @Autowired
    BookService bookService;

    @RequestMapping("")
    public String search(@RequestParam("q") String query, Model model) {
        List<Book> bookList = bookService.findBook(query);
        model.addAttribute("QueryString", query);
        model.addAttribute("ResultList", bookList);
        return "search/result";
    }
}
