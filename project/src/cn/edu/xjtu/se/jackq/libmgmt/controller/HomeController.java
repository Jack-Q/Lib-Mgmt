package cn.edu.xjtu.se.jackq.libmgmt.controller;

import cn.edu.xjtu.se.jackq.libmgmt.entity.User;
import cn.edu.xjtu.se.jackq.libmgmt.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;


@Controller
@RequestMapping("/")
public class HomeController {
    private static final Log logger = LogFactory.getLog(HomeController.class);

    @Autowired
    private UserService userService;

    @RequestMapping()
    public String index(Model model){
        logger.debug("Requesting Home Page: Home - Index");
        model.addAttribute("userList", userService.listUser());
        return "home/index";
    }

    @RequestMapping( path = "add", method = RequestMethod.GET)
    public String addGet(@ModelAttribute("User") User user){
        logger.debug("request Home Add ");
        return "home/add";
    }

    @RequestMapping(path = "add", method = RequestMethod.POST)
    public String addPost(@ModelAttribute("User") User user, Map<String, Object>map){
        map.put("indexMessageId", "user.add.success");
        userService.addUser(user);
        return "redirect:/";
    }
}
