package com.xshop.web;

import com.xshop.model.AdminResults;
import com.xshop.model.Results;
import com.xshop.service.AdminService;
import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/api/*")
public class AdminApiServlet extends HttpServlet {
    private AdminService adminService = new AdminService();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");

        String path = req.getPathInfo();
        if (path == null) path = "";

        String json;
        switch (path) {
            case "/getAllUser": {
                String page = req.getParameter("page");
                String limit = req.getParameter("limit");
                int offset = (Integer.parseInt(page) - 1) * Integer.parseInt(limit);
                AdminResults ar = adminService.getAllUser(offset, Integer.parseInt(limit));
                json = gson.toJson(ar);
                break;
            }
            case "/getAllShop": {
                String page = req.getParameter("page");
                String limit = req.getParameter("limit");
                int offset = (Integer.parseInt(page) - 1) * Integer.parseInt(limit);
                AdminResults ar = adminService.getAllShop(offset, Integer.parseInt(limit));
                json = gson.toJson(ar);
                break;
            }
            case "/getAllCart": {
                String page = req.getParameter("page");
                String limit = req.getParameter("limit");
                int offset = (Integer.parseInt(page) - 1) * Integer.parseInt(limit);
                AdminResults ar = adminService.getAllCart(offset, Integer.parseInt(limit));
                json = gson.toJson(ar);
                break;
            }
            case "/getIndex": {
                json = gson.toJson(adminService.getIndex());
                break;
            }
            default:
                json = gson.toJson(new Results(0, "未知请求"));
        }
        resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");

        String path = req.getPathInfo();
        if (path == null) path = "";

        String json;
        switch (path) {
            case "/addUser": {
                String username = req.getParameter("username");
                String password = req.getParameter("password");
                String nickname = req.getParameter("nickname");
                json = gson.toJson(adminService.addUser(username, password, nickname));
                break;
            }
            case "/deleteUser": {
                int userID = Integer.parseInt(req.getParameter("userID"));
                json = gson.toJson(adminService.deleteUser(userID));
                break;
            }
            case "/changeUser": {
                com.xshop.model.User user = new com.xshop.model.User();
                user.setID(Integer.parseInt(req.getParameter("userID")));
                user.setUsername(req.getParameter("username"));
                String pwd = req.getParameter("password");
                if (pwd != null && !pwd.isEmpty()) {
                    user.setPassword(pwd);
                }
                user.setNickname(req.getParameter("nickname"));
                user.setAvatar(req.getParameter("avatar"));
                user.setSign(req.getParameter("sign"));
                user.setSite(req.getParameter("site"));
                json = gson.toJson(adminService.changeUserInfo(user));
                break;
            }
            case "/addShop": {
                com.xshop.model.Shop shop = new com.xshop.model.Shop();
                shop.setID(Integer.parseInt(req.getParameter("id")));
                shop.setName(req.getParameter("name"));
                shop.setPrice(Float.parseFloat(req.getParameter("price")));
                shop.setOldPrice(Float.parseFloat(req.getParameter("oldPrice")));
                shop.setDescription(req.getParameter("description"));
                shop.setImg(req.getParameter("img"));
                shop.setSort(Integer.parseInt(req.getParameter("sort")));
                shop.setOther(req.getParameter("other"));
                json = gson.toJson(adminService.addShop(shop));
                break;
            }
            case "/changeShop": {
                com.xshop.model.Shop shop = new com.xshop.model.Shop();
                shop.setID(Integer.parseInt(req.getParameter("shopID")));
                shop.setName(req.getParameter("name"));
                String price = req.getParameter("price");
                if (price != null && !price.isEmpty()) shop.setPrice(Float.parseFloat(price));
                String oldPrice = req.getParameter("oldPrice");
                if (oldPrice != null && !oldPrice.isEmpty()) shop.setOldPrice(Float.parseFloat(oldPrice));
                shop.setDescription(req.getParameter("description"));
                shop.setImg(req.getParameter("img"));
                String sort = req.getParameter("sort");
                if (sort != null && !sort.isEmpty()) shop.setSort(Integer.parseInt(sort));
                shop.setOther(req.getParameter("other"));
                json = gson.toJson(adminService.changeShopInform(shop));
                break;
            }
            case "/deleteShop": {
                int shopID = Integer.parseInt(req.getParameter("shopID"));
                json = gson.toJson(adminService.deleteShop(shopID));
                break;
            }
            case "/changePassword": {
                String oldPassword = req.getParameter("oldPassword");
                String newPassword = req.getParameter("newPassword");
                json = gson.toJson(adminService.changePassword(oldPassword, newPassword));
                break;
            }
            default:
                json = gson.toJson(new Results(0, "未知请求"));
        }
        resp.getWriter().write(json);
    }
}
