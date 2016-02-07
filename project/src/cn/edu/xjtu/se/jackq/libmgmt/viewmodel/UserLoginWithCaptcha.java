package cn.edu.xjtu.se.jackq.libmgmt.viewmodel;

/**
 * Created by Jack on 2/4/2016.
 */
public class UserLoginWithCaptcha extends UserLogin {
    public UserLoginWithCaptcha(){
        super();
    }

    private String captcha;

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
