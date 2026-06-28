package com.xshop.model;

import java.util.Date;

public class User {
    private int id;
    private String username;
    private String nickname;
    private String password;
    private String avatar;
    private String sign;
    private String site;
    private Date lastLogin;
    private Date nowLogin;
    private String token;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getID() { return id; }
    public void setID(int id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public String getSign() { return sign; }
    public void setSign(String sign) { this.sign = sign; }
    public String getSite() { return site; }
    public void setSite(String site) { this.site = site; }
    public Date getLastLogin() { return lastLogin; }
    public void setLastLogin(Date lastLogin) { this.lastLogin = lastLogin; }
    public Date getNowLogin() { return nowLogin; }
    public void setNowLogin(Date nowLogin) { this.nowLogin = nowLogin; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
