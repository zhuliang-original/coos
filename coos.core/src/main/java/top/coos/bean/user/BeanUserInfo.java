package top.coos.bean.user;

import top.coos.Support;
import top.coos.annotation.Column;
import top.coos.annotation.PrimaryKey;

public class BeanUserInfo extends Support {

    @Override
    public BeanUserInfo clone() {
        BeanUserInfo object = (BeanUserInfo) super.clone();
        return object;
    }

    /**
     * 
     */
    private static final long serialVersionUID = 7038978348899755221L;

    @PrimaryKey
    @Column(name = "userid", comment = "编号")
    private String userid;

    @Column(name = "username", comment = "用户名")
    private String username;

    @Column(name = "loginname", comment = "登录名")
    private String loginname;

    @Column(name = "password", comment = "密码")
    private String password;

    @Column(name = "photo", comment = "照片")
    private String photo;

    @Column(name = "phone", comment = "手机")
    private String phone;

    @Column(name = "email", comment = "邮箱")
    private String email;

    @Column(name = "onlinestatus", comment = "在线状态")
    private Integer onlinestatus;

    @Column(name = "status", comment = "状态")
    private Integer status;

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getOnlinestatus() {
        return onlinestatus;
    }

    public void setOnlinestatus(Integer onlinestatus) {
        this.onlinestatus = onlinestatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
