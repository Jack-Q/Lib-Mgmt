package cn.edu.xjtu.se.jackq.libmgmt.session;

import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes(names = "SessionUser")
public class SessionUser {
    private String userName = null;
    private boolean authorized = false;

    private int id = 0;
    private String name = null;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
