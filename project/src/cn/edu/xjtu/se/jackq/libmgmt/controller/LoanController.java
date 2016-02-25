package cn.edu.xjtu.se.jackq.libmgmt.controller;

import cn.edu.xjtu.se.jackq.libmgmt.annotation.Auth;
import cn.edu.xjtu.se.jackq.libmgmt.entity.User;
import cn.edu.xjtu.se.jackq.libmgmt.entity.UserRole;
import cn.edu.xjtu.se.jackq.libmgmt.service.UserService;
import cn.edu.xjtu.se.jackq.libmgmt.session.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@Auth(userRoles = UserRole.LIBRARIAN)
@RequestMapping("/loan")
public class LoanController {
    @Autowired
    private UserService userService;

    @RequestMapping("lend")
    public String lendPrep(Model model) {
        model.addAttribute("UserPageTitle", "Lend books");
        model.addAttribute("NextPage", "/loan/lend/");
        return "loan/user";
    }

    @RequestMapping("lend/{UserId}")
    public String lend(Model model, @PathVariable("UserId") int userId) {
        User user = userService.getUser(userId);
        // Only Students Can borrow books
        if (user == null || !user.getRoles().contains(UserRole.STUDENT)) {
            return "redirect:/loan/lend";
        }
        model.addAttribute("CurrentUser", user);
        return "loan/lend";
    }

    @RequestMapping(value = "lendAjax", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String doLend() {
        int period = 30;
        return "{\"success\": true, \"period\": " + period + "}";
    }

    @RequestMapping("return") // Since return is a keyword in Java, this method use returnBook as its name instead
    public String returnBookPrep(Model model) {
        model.addAttribute("UserPageTitle", "Return books");
        model.addAttribute("NextPage", "/loan/return/");
        return "loan/user";
    }


    @RequestMapping("return/{UserId}")
    public String returnBook(Model model, @PathVariable("UserId") int userId) {
        User user = userService.getUser(userId);
        // Only Students Can borrow books
        if (user == null || !user.getRoles().contains(UserRole.STUDENT)) {
            return "redirect:/loan/return";
        }
        model.addAttribute("CurrentUser", user);
        return "loan/return";
    }

    @RequestMapping("status")
    @Auth(userRoles = UserRole.STUDENT)
    public String status(HttpSession httpSession) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("Auth");
        int userId = sessionUser.getId();
        return "loan/status";
    }
}
