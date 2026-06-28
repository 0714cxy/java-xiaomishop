package com.xshop.service;

import com.xshop.dao.UserDao;
import com.xshop.model.Results;
import com.xshop.model.User;
import com.xshop.util.MD5;
import com.xshop.util.TokenUtil;
import java.util.Date;

public class UserService {
    private UserDao userDao = new UserDao();

    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    public Results userRegister(String username, String password, String nickname) {
        if (userDao.getUserByUsername(username) != 0) {
            return new Results(0, "用户名已存在");
        }
        if (userDao.registerUser(username, password, nickname) != 1) {
            return new Results(0, "未知错误");
        }
        return new Results(1, "注册成功");
    }

    public Results userLogin(String username, String password) {
        String userPass = userDao.getUserPassByUsername(username);
        if (userPass == null) {
            return new Results(0, "用户名不存在");
        }
        if (!MD5.crypt(password).equals(userPass)) {
            return new Results(0, "用户名或密码错误");
        }
        String token = MD5.crypt(password + new Date().toString());
        userDao.insertToken(username, token);
        int ID = userDao.getUserIdByUsername(username);
        User user = new User();
        user.setID(ID);
        user.setToken(token);
        userDao.insertLoginTime(username);
        return new Results(1, "登录成功", user);
    }

    public Results getInfo(int userID, String token) {
        if (!TokenUtil.verify(userID, token)) {
            return new Results(0, "非法访问");
        }
        return new Results(1, "获取用户信息成功", userDao.getUserInfoById(userID));
    }

    public Results changeUserInfo(User user) {
        if (!TokenUtil.verify(user.getID(), user.getToken())) {
            return new Results(0, "非法访问");
        }
        userDao.changeUserInfo(user);
        return new Results(1, "修改用户信息成功");
    }

    public Results changePassword(int id, String token, String oldPassword, String password) {
        if (!TokenUtil.verify(id, token)) {
            return new Results(0, "非法访问");
        }
        User user = userDao.getUserById(id);
        if (!user.getPassword().equals(MD5.crypt(oldPassword))) {
            return new Results(0, "原密码不正确");
        }
        userDao.changePassword(id, MD5.crypt(password));
        return new Results(1, "修改成功");
    }

    public Results getUserSite(int userID) {
        return new Results(1, "获取地址成功", userDao.getUserSite(userID));
    }
}
