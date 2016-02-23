package cn.edu.xjtu.se.jackq.libmgmt.session;

import cn.edu.xjtu.se.jackq.libmgmt.entity.UserRole;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.io.Serializable;
import java.util.List;

@SessionAttributes(names = "SessionUser")
public class SessionUser implements Serializable {
    private static final long serialVersionUID = 6654482229053024059L;
    private String userName = null;
    private boolean authorized = false;

    private int id = 0;
    private String name = null;
    private List<UserRole> roles;

    public boolean isAdmin() {
        return getRoles() != null && getRoles().contains(UserRole.ADMIN);
    }

    public boolean isLibrarian() {
        return getRoles() != null && getRoles().contains(UserRole.LIBRARIAN);
    }

    public boolean isStudent() {
        return getRoles() != null && getRoles().contains(UserRole.STUDENT);
    }

    public boolean isGuest() {
        return getRoles() != null && getRoles().contains(UserRole.GUEST);
    }

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

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }
}
