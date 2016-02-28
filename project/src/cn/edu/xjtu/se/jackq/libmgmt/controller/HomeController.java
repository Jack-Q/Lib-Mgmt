package cn.edu.xjtu.se.jackq.libmgmt.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping({"", "/home"})
public class HomeController {
    private static final Log logger = LogFactory.getLog(HomeController.class);


    @RequestMapping({"/", "/index"})
    public String index(Model model) {
        logger.debug("Requesting Home Page: Home - Index");
        return "home/index";
    }

    @RequestMapping(value = "/about")
    public String about() {
        return "home/about";
    }
}
