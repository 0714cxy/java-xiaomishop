package com.xshop.service;

import com.xshop.dao.AdminDao;
import com.xshop.dao.UserDao;
import com.xshop.model.*;
import com.xshop.util.MD5;

public class AdminService {
    private AdminDao adminDao = new AdminDao();
    private UserDao userDao = new UserDao();

    public Results adminLogin(String username, String password) {
        String adminPassword = adminDao.getAdminPass(username);
        if (MD5.crypt(password).equals(adminPassword)) {
            return new Results(1, "登录成功");
        }
        return new Results(0, "账号或密码错误");
    }

    public AdminResults getAllUser(Integer offset, Integer limit) {
        return new AdminResults(0, "获取成功", adminDao.getAllUser(offset, limit), adminDao.getUserCount());
    }

    public Results addUser(String username, String password, String nickname) {
        adminDao.addUser(username, password, nickname);
        return new Results(1, "添加成功");
    }

    public Results deleteUser(int userID) {
        adminDao.deleteUser(userID);
        return new Results(1, "删除成功");
    }

    public AdminResults getAllShop(Integer offset, Integer limit) {
        return new AdminResults(0, "获取成功", adminDao.getAllShop(offset, limit), adminDao.getShopCount());
    }

    public Results addShop(Shop shop) {
        adminDao.addShop(shop);
        return new Results(1, "添加成功");
    }

    public Results changeShopInform(Shop shop) {
        adminDao.changeShopInform(shop);
        return new Results(1, "修改成功");
    }

    public Results deleteShop(int shopID) {
        adminDao.deleteShop(shopID);
        return new Results(1, "删除成功");
    }

    public AdminResults getAllCart(Integer offset, Integer limit) {
        return new AdminResults(0, "获取成功", adminDao.getAllCart(offset, limit), adminDao.getCartCount());
    }

    public Results getIndex() {
        IndexPage indexPage = new IndexPage();
        indexPage.setShop(adminDao.getShopCount());
        indexPage.setUser(adminDao.getUserCount());
        return new Results(1, "获取成功", indexPage);
    }

    public Results changePassword(String oldPassword, String newPassword) {
        Results results = adminLogin("admin", oldPassword);
        if (results.getCode() == 1) {
            adminDao.changePassword(MD5.crypt(newPassword));
            return new Results(1, "密码修改成功");
        }
        return new Results(0, "旧密码不正确");
    }

    public Results changeUserInfo(User user) {
        userDao.changeUserInfo(user);
        return new Results(1, "修改用户信息成功");
    }
}
