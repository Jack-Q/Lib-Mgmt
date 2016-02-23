package cn.edu.xjtu.se.jackq.libmgmt.controller;

import cn.edu.xjtu.se.jackq.libmgmt.annotation.PartialView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/book/")
public class BookController {

    @RequestMapping({"", "index"})
    public String index() {
        return "book/index";
    }

    @RequestMapping("manage")
    public String manage() {
        return this.manage(1);
    }

    @RequestMapping("manage/{page}")
    public String manage(@PathVariable(value = "page") int page) {
        return "book/manage";
    }

    @RequestMapping("comment/{bookCode}")
    @PartialView
    public String comment(@PathVariable String bookCode) {
        return "book/comment";
    }

    @RequestMapping({"detail", "detail/{bookCode}"})
    public String book(@PathVariable("bookCode") String bookCode) {
        return "book/detail";
    }

}
