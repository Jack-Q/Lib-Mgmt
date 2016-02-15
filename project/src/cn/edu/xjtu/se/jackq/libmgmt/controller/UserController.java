package cn.edu.xjtu.se.jackq.libmgmt.controller;

import cn.edu.xjtu.se.jackq.libmgmt.annotation.Auth;
import cn.edu.xjtu.se.jackq.libmgmt.entity.User;
import cn.edu.xjtu.se.jackq.libmgmt.entity.UserRole;
import cn.edu.xjtu.se.jackq.libmgmt.service.UserService;
import cn.edu.xjtu.se.jackq.libmgmt.session.SessionUser;
import cn.edu.xjtu.se.jackq.libmgmt.viewmodel.UserLogin;
import cn.edu.xjtu.se.jackq.libmgmt.viewmodel.UserRegister;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Base64;

@Controller
@RequestMapping("/user/")
@Auth
public class UserController {
    private static final Log logger = LogFactory.getLog(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"", "index"})
    public String index(){
        return "user/index";
    }

    @RequestMapping(value = "manage")
    @Auth(userRoles = {UserRole.ADMIN, UserRole.LIBRARIAN})
    public String manage() {
        return "user/manage";
    }

    @Auth(allowAnonymous = true)
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(@ModelAttribute("UserLogin") UserLogin userLogin, @ModelAttribute("returnTo") String returnToUrlPara){
        // String returnToUrl = decodeRedirectUrlPara(returnToUrlPara);
        return "user/login";
    }

    @Auth(allowAnonymous = true)
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String doLogin(@ModelAttribute("UserLogin") UserLogin userLogin,
                          @ModelAttribute("returnTo") String returnToUrlPara,
                          RedirectAttributes redirectAttributes,
                          HttpSession session,
                          Model model){
        String returnToUrl = decodeRedirectUrlPara(returnToUrlPara);
        SessionUser sessionUser = (SessionUser) session.getAttribute("Auth");
        if(sessionUser == null){
            sessionUser = new SessionUser();
            session.setAttribute("Auth", sessionUser);
        }
        if(sessionUser.isAuthorized()){
            userService.doLogout(sessionUser);
        }
        boolean loginResult = userService.doLogin(userLogin.getUserName(), userLogin.getPassword(), sessionUser);
        if(loginResult){
            redirectAttributes.addFlashAttribute("indexMessageId", "user.login.success");
            return "redirect:" + returnToUrl;
        }
        model.addAttribute("errorMessageId", "user.login.error");
        return "user/login";
    }

    @Auth(allowAnonymous = true) // Set it allow anonymous to prevent infinite loop in login and logout
    @RequestMapping("logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes){
        SessionUser sessionUser = (SessionUser) session.getAttribute("Auth");
        if(sessionUser != null && sessionUser.isAuthorized()){
            userService.doLogout(sessionUser);
            redirectAttributes.addFlashAttribute("indexMessageId", "user.logout.success");
        }
        return "redirect:/";
    }

    @Auth(allowAnonymous = true)
    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String register(@ModelAttribute("UserRegister") UserRegister userRegister,  @ModelAttribute("returnTo") String returnToUrlPara){
        // String returnToUrl = decodeRedirectUrlPara(returnToUrlPara);
        return "user/register";
    }

    @Auth(allowAnonymous = true)
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String doRegister(@ModelAttribute("UserRegister") UserRegister userRegister,
                             @ModelAttribute("returnTo") String returnToUrlPara,
                             Model model, HttpSession session, RedirectAttributes redirectAttributes){

        String returnToUrl = decodeRedirectUrlPara(returnToUrlPara);

        // Validate Data
        String username = userRegister.getUserName();
        if(null == username || username.isEmpty() || username.length() > 25){
            model.addAttribute("errorMessageId", "user.register.error.username");
            return "user/register";
        }
        String password = userRegister.getPassword();
        if(null == password || password.isEmpty()){
            model.addAttribute("errorMessageId", "user.register.error.password");
            return "user/register";
        }
        String name = userRegister.getName();
        if(null == name || name.isEmpty()){
            model.addAttribute("errorMessageId", "user.register.error.name");
            return "user/register";
        }

        // Check Name Availability
        if(!userService.checkNameAvailability(username)){
            model.addAttribute("errorMessageId", "user.register.error.nameConflict");
            return "user/register";
        }

        //Create user
        User user = userService.addUser(username, password, name);
        if(null == user){
            model.addAttribute("errorMessageId", "user.register.error.create");
            return "user/register";
        }

        // Update profile
        user.setEmail(userRegister.getEmail());
        user.setPhoneNumber(userRegister.getPhoneNumber());
        user.setDateOfBirth(userRegister.getDateOfBirth());
        userService.updateUser(user);

        // Login user
        SessionUser sessionUser = (SessionUser) session.getAttribute("Auth");
        if(sessionUser == null){
            sessionUser = new SessionUser();
            session.setAttribute("Auth", sessionUser);
        }


        boolean loginResult = userService.doLogin(username, password, sessionUser);
        if(!loginResult){

            model.addAttribute("errorMessageId", "user.register.error.create");
            return "user/register";
        }

        // Redirect to index
        redirectAttributes.addFlashAttribute("indexMessageId", "user.register.success");
        return "redirect:" + returnToUrl;
    }


    private String decodeRedirectUrlPara(String redirectToUrPara){
        System.out.println("Return to Url " + redirectToUrPara);
        String returnToUrl;
        try {
            returnToUrl = new String(Base64.getDecoder().decode(redirectToUrPara));
            // Ensure the redirect url is not empty
            if(returnToUrl.isEmpty()){
                returnToUrl = "/";
            }
            // Ensure the redirect url is in current site
            if(returnToUrl.charAt(0) != '/'){
                returnToUrl = "/";
            }
        }catch(Exception e){
            returnToUrl = "/";
        }
        System.out.println("Decoded " + returnToUrl);
        return returnToUrl;
    }
}
