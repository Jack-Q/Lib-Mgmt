package cn.edu.xjtu.se.jackq.libmgmt.listener;


import cn.edu.xjtu.se.jackq.libmgmt.entity.User;
import cn.edu.xjtu.se.jackq.libmgmt.entity.UserRole;
import cn.edu.xjtu.se.jackq.libmgmt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;


public class ApplicationServiceInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private static boolean isInitialized = false;

    private String adminAccountUserName = "admin";
    private String adminAccountPassword = "admin";


    @Autowired
    UserService userService;

    private void initUserService() {
        if (null == userService.getUser(getAdminAccountUserName())) {
            User admin = userService.addUser(getAdminAccountUserName(), getAdminAccountPassword(), "Administrator");
            userService.setRole(admin.getId(), UserRole.ADMIN);
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!isInitialized) {
            initUserService();
            isInitialized = true;
        }
    }

    public String getAdminAccountUserName() {
        return adminAccountUserName;
    }

    public void setAdminAccountUserName(String adminAccountUserName) {
        this.adminAccountUserName = adminAccountUserName;
    }

    public String getAdminAccountPassword() {
        return adminAccountPassword;
    }

    public void setAdminAccountPassword(String adminAccountPassword) {
        this.adminAccountPassword = adminAccountPassword;
    }
}
