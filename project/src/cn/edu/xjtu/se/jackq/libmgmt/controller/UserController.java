package cn.edu.xjtu.se.jackq.libmgmt.controller;

import cn.edu.xjtu.se.jackq.libmgmt.service.UserService;
import cn.edu.xjtu.se.jackq.libmgmt.viewmodel.UserLogin;
import cn.edu.xjtu.se.jackq.libmgmt.viewmodel.UserRegister;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user/")
public class UserController {
    private static final Log logger = LogFactory.getLog(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login(@ModelAttribute("UserLogin") UserLogin userLogin){
        return "user/login";
    }

    @RequestMapping(path = "/register" )
    public String login(@ModelAttribute("UserRegister") UserRegister userRegister){
        return "user/register";
    }
}
