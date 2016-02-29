package cn.edu.xjtu.se.jackq.libmgmt.controller;

import cn.edu.xjtu.se.jackq.libmgmt.annotation.Auth;
import cn.edu.xjtu.se.jackq.libmgmt.entity.User;
import cn.edu.xjtu.se.jackq.libmgmt.entity.UserRole;
import cn.edu.xjtu.se.jackq.libmgmt.service.UserService;
import cn.edu.xjtu.se.jackq.libmgmt.session.SessionUser;
import cn.edu.xjtu.se.jackq.libmgmt.viewmodel.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Base64;
import java.util.List;

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
    @Auth(userRoles = {UserRole.ADMIN, UserRole.LIBRARIAN})
    public String manage(Model model) {
        model.addAttribute("ReaderList", userService.listReader());
        return "user/manage";
    }

    /**
     * Manage Librarian Account
     */
    @RequestMapping(value = "admin")
    @Auth(userRoles = UserRole.ADMIN)
    public String admin(Model model) {

        model.addAttribute("LibrarianList", userService.listLibrarian());
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
                          HttpServletRequest request,
                          Model model) {
        String returnToUrl = decodeRedirectUrlPara(returnToUrlPara, request.getContextPath());
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
    public String register(@ModelAttribute("UserRegister") UserRegister userRegister,
                           @ModelAttribute("returnTo") String returnToUrlPara) {
        // String returnToUrl = decodeRedirectUrlPara(returnToUrlPara);
        return "user/register";
    }

    @Auth(allowAnonymous = true)
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String doRegister(@ModelAttribute("UserRegister") UserRegister userRegister,
                             @ModelAttribute("returnTo") String returnToUrlPara,
                             Model model,
                             HttpSession session,
                             HttpServletRequest request,
                             RedirectAttributes redirectAttributes) {

        String returnToUrl = decodeRedirectUrlPara(returnToUrlPara, request.getContextPath());

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

    @Auth(userRoles = {UserRole.ADMIN, UserRole.LIBRARIAN})
    @RequestMapping(value = "changeRoleAjax", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String doChangeRoleAjax(@ModelAttribute("userId") int userId,
                                   @ModelAttribute("roleName") String roleName,
                                   HttpServletResponse response) {
        response.setContentType("application/json");
        UserRole role = UserRole.valueOf(roleName);


        if (roleName == null || !userService.isInRole(userId, UserRole.GUEST) && !userService.isInRole(userId, UserRole.STUDENT)) {
            return "{\"fail\": true}";
        }

        if (userService.setRole(userId, role)) {

            return "{\"success\": true}";
        } else {
            return "{\"fail\": true}";
        }

    }


    @Auth(userRoles = {UserRole.ADMIN, UserRole.LIBRARIAN})
    @RequestMapping(value = "resetPassword/{UserId}", method = RequestMethod.GET)
    public String resetPassword(@PathVariable("UserId") int userId,
                                @ModelAttribute("UserResetPassword") UserResetPassword userResetPassword,
                                Model model,
                                HttpSession httpSession) {
        User user = userService.getUser(userId);
        if (user == null || user.getRoles().contains(UserRole.ADMIN)) {
            return "redirect:/error/argument";
        }

        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("Auth");

        // Librarian cannot change his password
        if (user.getRoles().contains(UserRole.LIBRARIAN) && !sessionUser.getRoles().contains(UserRole.ADMIN)) {
            return "redirect:/error/denied";
        }

        model.addAttribute("CurrentUser", user);
        return "user/resetPassword";
    }

    @Auth(userRoles = {UserRole.ADMIN, UserRole.LIBRARIAN})
    @RequestMapping(value = "resetPassword/{UserId}", method = RequestMethod.POST)
    public String doResetPassword(@PathVariable("UserId") int userId,
                                  @ModelAttribute("UserResetPassword") UserResetPassword userResetPassword,
                                  Model model,
                                  RedirectAttributes redirectAttributes,
                                  HttpSession httpSession) {

        User user = userService.getUser(userId);
        if (user == null || user.getRoles().contains(UserRole.ADMIN)) {
            return "redirect:error/argument";
        }

        model.addAttribute("CurrentUser", user);

        if (userResetPassword.getPassword() == null) {
            model.addAttribute("errorMessageId", "user.resetPassword.error.password");
            return "user/resetPassword";
        }


        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("Auth");

        // Librarian cannot change his password
        if (user.getRoles().contains(UserRole.LIBRARIAN) && !sessionUser.getRoles().contains(UserRole.ADMIN)) {
            return "redirect:/error/denied";
        }

        if (!userService.changePassword(userId, userResetPassword.getPassword())) {
            model.addAttribute("errorMessageId", "user.resetPassword.error.failed");
            return "user/resetPassword";
        }
        redirectAttributes.addFlashAttribute("indexMessageId", "user.resetPassword.success");

        // For librarian, redirect to admin page
        if (user.getRoles().contains(UserRole.LIBRARIAN)) {
            return "redirect:/user/admin";
        }
        return "redirect:/user/manage";
    }

    @RequestMapping(value = "edit/{UserId}", method = RequestMethod.GET)
    @Auth(userRoles = {UserRole.ADMIN, UserRole.LIBRARIAN})
    public String edit(@PathVariable("UserId") int userId,
                       @ModelAttribute("UserEdit") UserEdit userEdit,
                       HttpSession httpSession,
                       Model model) {
        User user = userService.getUser(userId);
        if (user == null || user.getRoles().contains(UserRole.ADMIN)) {
            return "redirect:/error/argument";
        }

        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("Auth");

        // Librarian cannot change his password
        if (user.getRoles().contains(UserRole.LIBRARIAN) && !sessionUser.getRoles().contains(UserRole.ADMIN)) {
            return "redirect:/error/denied";
        }


        userEdit.setName(user.getName());
        userEdit.setEmail(user.getEmail());
        userEdit.setPhoneNumber(user.getPhoneNumber());
        userEdit.setDateOfBirth(user.getDateOfBirth());

        model.addAttribute("CurrentUser", user);
        return "user/edit";
    }

    @Auth(userRoles = {UserRole.ADMIN, UserRole.LIBRARIAN})
    @RequestMapping(value = "edit/{UserId}", method = RequestMethod.POST)
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

    @Auth(userRoles = UserRole.ADMIN)
    @RequestMapping("selectLibrarian/{userId}")
    public String selectLibrarian(@PathVariable int userId, RedirectAttributes redirectAttributes) {
        User user = userService.getUser(userId);
        if (user == null || user.getRoles().contains(UserRole.ADMIN)) {
            return "redirect:error/argument";
        }

        if (!userService.setRole(userId, UserRole.LIBRARIAN)) {
            redirectAttributes.addFlashAttribute("indexMessageId", "user.selectLibrarian.failed");
        } else {

            redirectAttributes.addFlashAttribute("indexMessageId", "user.selectLibrarian.success");
        }

        return "redirect:/user/manage";
    }

    @Auth(userRoles = UserRole.ADMIN)
    @RequestMapping("deselectLibrarian/{userId}")
    public String deselectLibrarian(@PathVariable int userId, RedirectAttributes redirectAttributes) {
        User user = userService.getUser(userId);
        if (user == null || !user.getRoles().contains(UserRole.LIBRARIAN)) {
            return "redirect:error/argument";
        }

        if (!userService.setRole(userId, UserRole.GUEST)) {
            redirectAttributes.addFlashAttribute("indexMessageId", "user.deselectLibrarian.failed");
        } else {

            redirectAttributes.addFlashAttribute("indexMessageId", "user.deselectLibrarian.success");
        }

        return "redirect:/user/admin";
    }

    @Auth(userRoles = UserRole.LIBRARIAN)
    @RequestMapping(value = "search", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String searchAjax(@RequestParam("q") String queryString,
                             @RequestParam("byId") boolean isById,
                             @RequestParam("byName") boolean isByName) {
        // return "{\"success\":" + isByName + "}" + isById + queryString;
        List<User> searchResult = userService.searchUser(queryString, isByName, isById);
        return encodeJsonForSearch(searchResult, true);
    }

    private String encodeJsonForSearch(List<User> userList, boolean status) {
        StringBuilder stringBuilder = new StringBuilder();
        // Prepend Status
        stringBuilder.append("{\"success\":");
        stringBuilder.append(status);
        stringBuilder.append(",\"data\": [");
        // Close JSON Data
        if (status) {
            for (User user : userList) {
                // Start Object
                stringBuilder.append("{");
                // User id
                stringBuilder.append("\"id\": ");
                stringBuilder.append(user.getId());
                stringBuilder.append(", ");
                // User username
                stringBuilder.append("\"username\": \"");
                stringBuilder.append(user.getUserName());
                stringBuilder.append("\", ");
                // User name
                stringBuilder.append("\"name\": \"");
                stringBuilder.append(user.getName());
                stringBuilder.append("\"");
                // End Object
                stringBuilder.append("},");
            }
            if (stringBuilder.lastIndexOf(",") == stringBuilder.length() - 1) {
                stringBuilder.delete(stringBuilder.lastIndexOf(","), stringBuilder.lastIndexOf(",") + 1); // Remove extra comma
            }
        }
        // Return String Value
        stringBuilder.append("]}");
        return stringBuilder.toString();
    }

    private String decodeRedirectUrlPara(String redirectToUrPara, String contextPath) {
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
            if (!returnToUrl.startsWith(contextPath)) {
                returnToUrl = "/";
            }
            returnToUrl = returnToUrl.substring(contextPath.length());
        } catch (Exception e) {
            returnToUrl = "/";
        }
        System.out.println("Decoded " + returnToUrl);
        return returnToUrl;
    }


}
