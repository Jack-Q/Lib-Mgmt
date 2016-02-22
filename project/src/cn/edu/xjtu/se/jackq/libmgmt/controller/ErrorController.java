package cn.edu.xjtu.se.jackq.libmgmt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/error/")
public class ErrorController {
    @RequestMapping("denied")
    public String denied() {
        return "error/denied";
    }

    @RequestMapping("resource")
    public String resource() {
        return "error/resource";
    }

    @RequestMapping("argument")
    public String argument() {
        return "error/argument";
    }
}
