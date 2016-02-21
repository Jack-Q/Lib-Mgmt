package cn.edu.xjtu.se.jackq.libmgmt.controller;

import cn.edu.xjtu.se.jackq.libmgmt.annotation.Auth;
import cn.edu.xjtu.se.jackq.libmgmt.entity.User;
import cn.edu.xjtu.se.jackq.libmgmt.entity.UserRole;
import cn.edu.xjtu.se.jackq.libmgmt.service.UserService;
import cn.edu.xjtu.se.jackq.libmgmt.session.SessionUser;
import cn.edu.xjtu.se.jackq.libmgmt.viewmodel.UserChangePassword;
import cn.edu.xjtu.se.jackq.libmgmt.viewmodel.UserInformation;
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
    public String index(Model model, HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("Auth");
        User currentUser = userService.getUser(sessionUser.getId());
        model.addAttribute("User", currentUser);
        return "user/index";
    }

    /**
     * Manage Reader Accounts
     */
    @RequestMapping(value = "manage")
    // @Auth(userRoles = {UserRole.ADMIN, UserRole.LIBRARIAN})
    public String manage(HttpSession httpSession, Model model) {
        model.addAttribute("ReaderList", userService.listReader());
        return "user/manage";
    }

    /**
     * Manage Librarian Account
     */
    @RequestMapping(value = "admin")
    @Auth(userRoles = UserRole.ADMIN)
    public String admin() {
        return "user/admin";
    }

    @Auth(allowAnonymous = true)
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(@ModelAttribute("UserLogin") UserLogin userLogin, @ModelAttribute("returnTo") String returnToUrlPara) {
        // String returnToUrl = decodeRedirectUrlPara(returnToUrlPara);
        return "user/login";
    }

    @Auth(allowAnonymous = true)
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String doLogin(@ModelAttribute("UserLogin") UserLogin userLogin,
                          @ModelAttribute("returnTo") String returnToUrlPara,
                          RedirectAttributes redirectAttributes,
                          HttpSession session,
                          Model model) {
        String returnToUrl = decodeRedirectUrlPara(returnToUrlPara);
        SessionUser sessionUser = (SessionUser) session.getAttribute("Auth");
        if (sessionUser == null) {
            sessionUser = new SessionUser();
            session.setAttribute("Auth", sessionUser);
        }
        if (sessionUser.isAuthorized()) {
            userService.doLogout(sessionUser);
        }
        boolean loginResult = userService.doLogin(userLogin.getUserName(), userLogin.getPassword(), sessionUser);
        if (loginResult) {
            redirectAttributes.addFlashAttribute("indexMessageId", "user.login.success");
            return "redirect:" + returnToUrl;
        }
        model.addAttribute("errorMessageId", "user.login.error");
        return "user/login";
    }

    @Auth(allowAnonymous = true) // Set it allow anonymous to prevent infinite loop in login and logout
    @RequestMapping("logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("Auth");
        if (sessionUser != null && sessionUser.isAuthorized()) {
            userService.doLogout(sessionUser);
            redirectAttributes.addFlashAttribute("indexMessageId", "user.logout.success");
        }
        return "redirect:/";
    }

    @Auth(allowAnonymous = true)
    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String register(@ModelAttribute("UserRegister") UserRegister userRegister, @ModelAttribute("returnTo") String returnToUrlPara) {
        // String returnToUrl = decodeRedirectUrlPara(returnToUrlPara);
        return "user/register";
    }

    @Auth(allowAnonymous = true)
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String doRegister(@ModelAttribute("UserRegister") UserRegister userRegister,
                             @ModelAttribute("returnTo") String returnToUrlPara,
                             Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String returnToUrl = decodeRedirectUrlPara(returnToUrlPara);

        // Validate Data
        String username = userRegister.getUserName();
        if (null == username || username.isEmpty() || username.length() > 25) {
            model.addAttribute("errorMessageId", "user.register.error.username");
            return "user/register";
        }
        String password = userRegister.getPassword();
        if (null == password || password.isEmpty()) {
            model.addAttribute("errorMessageId", "user.register.error.password");
            return "user/register";
        }
        String name = userRegister.getName();
        if (null == name || name.isEmpty()) {
            model.addAttribute("errorMessageId", "user.register.error.name");
            return "user/register";
        }

        // Check Name Availability
        if (!userService.checkNameAvailability(username)) {
            model.addAttribute("errorMessageId", "user.register.error.nameConflict");
            return "user/register";
        }

        //Create user
        User user = userService.addUser(username, password, name);
        if (null == user) {
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
        if (sessionUser == null) {
            sessionUser = new SessionUser();
            session.setAttribute("Auth", sessionUser);
        }


        boolean loginResult = userService.doLogin(username, password, sessionUser);
        if (!loginResult) {

            model.addAttribute("errorMessageId", "user.register.error.create");
            return "user/register";
        }

        // Redirect to index
        redirectAttributes.addFlashAttribute("indexMessageId", "user.register.success");
        return "redirect:" + returnToUrl;
    }


    @RequestMapping(value = "changePassword", method = RequestMethod.GET)
    public String changePassword(@ModelAttribute("UserChangePassword") UserChangePassword userChangePassword) {
        return "user/changePassword";
    }

    @RequestMapping(value = "changePassword", method = RequestMethod.POST)
    public String doChangePassword(@ModelAttribute("UserChangePassword") UserChangePassword userChangePassword,
                                   Model model,
                                   HttpSession httpSession,
                                   RedirectAttributes redirectAttributes) {

        // Validate Data
        String currentPassword = userChangePassword.getCurrentPassword();
        if (null == currentPassword || currentPassword.isEmpty()) {
            model.addAttribute("errorMessageId", "user.changePassword.error.currentPassword");
            return "user/changePassword";
        }

        String newPassword = userChangePassword.getNewPassword();
        if (null == newPassword || newPassword.isEmpty()) {
            model.addAttribute("errorMessageId", "user.changePassword.error.newPassword");
            return "user/changePassword";
        }

        // Get Current User
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("Auth");

        // Check current password
        if (!userService.checkPassword(sessionUser.getId(), currentPassword)) {
            model.addAttribute("errorMessageId", "user.changePassword.error.wrongPassword");
            return "user/changePassword";
        }

        // Update Password
        if (!userService.changePassword(sessionUser.getId(), newPassword)) {
            model.addAttribute("errorMessageId", "user.changePassword.error.failed");
            return "user/changePassword";
        }

        redirectAttributes.addFlashAttribute("indexMessageId", "user.changePassword.success");
        return "redirect:/user/index";
    }

    @RequestMapping(value = "information", method = RequestMethod.GET)
    public String information(@ModelAttribute("UserInformation") UserInformation information, HttpSession httpSession) {
        // Retrieve current user information
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("Auth");
        User user = userService.getUser(sessionUser.getId());
        information.setName(user.getName());
        information.setEmail(user.getEmail());
        information.setPhoneNumber(user.getPhoneNumber());
        information.setDateOfBirth(user.getDateOfBirth());
        return "user/information";
    }

    @RequestMapping(value = "information", method = RequestMethod.POST)
    public String doInformation(@ModelAttribute("UserInformation") UserInformation userInformation,
                                Model model,
                                HttpSession httpSession,
                                RedirectAttributes redirectAttributes) {
        // Validate data
        // Only name is a required one
        if (null == userInformation.getName() || userInformation.getName().isEmpty()) {
            model.addAttribute("errorMessageId", "user.information.error.name");
            return "user/information";
        }

        // Update data
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("Auth");
        User user = userService.getUser(sessionUser.getUserName());
        user.setName(userInformation.getName());
        user.setEmail(userInformation.getEmail());
        user.setPhoneNumber(userInformation.getPhoneNumber());
        user.setDateOfBirth(userInformation.getDateOfBirth());
        if (!userService.updateUser(user)) {
            model.addAttribute("errorMessageId", "user.information.error.failed");
            return "user/information";
        }
        // Update session data
        sessionUser.setName(user.getName());
        redirectAttributes.addFlashAttribute("indexMessageId", "user.information.success");
        return "redirect:/user/index";
    }

    private String decodeRedirectUrlPara(String redirectToUrPara) {
        System.out.println("Return to Url " + redirectToUrPara);
        String returnToUrl;
        try {
            returnToUrl = new String(Base64.getDecoder().decode(redirectToUrPara));
            // Ensure the redirect url is not empty
            if (returnToUrl.isEmpty()) {
                returnToUrl = "/";
            }
            // Ensure the redirect url is in current site
            if (returnToUrl.charAt(0) != '/') {
                returnToUrl = "/";
            }
        } catch (Exception e) {
            returnToUrl = "/";
        }
        System.out.println("Decoded " + returnToUrl);
        return returnToUrl;
    }


}
