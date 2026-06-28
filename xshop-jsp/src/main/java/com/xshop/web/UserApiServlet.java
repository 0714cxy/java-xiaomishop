package com.xshop.web;

import com.xshop.model.Results;
import com.xshop.service.UserService;
import com.xshop.util.TokenUtil;
import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/v1/user/*")
public class UserApiServlet extends HttpServlet {
    private UserService userService = new UserService();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        String path = req.getPathInfo();
        String json;

        switch (path) {
            case "/getInfo": {
                int userID = Integer.parseInt(req.getParameter("userID"));
                String token = req.getParameter("token");
                json = gson.toJson(userService.getInfo(userID, token));
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
        String json;

        switch (path) {
            case "/register": {
                String username = req.getParameter("username");
                String password = req.getParameter("password");
                String nickname = req.getParameter("nickname");
                json = gson.toJson(userService.userRegister(username, password, nickname));
                break;
            }
            case "/login": {
                String username = req.getParameter("username");
                String password = req.getParameter("password");
                json = gson.toJson(userService.userLogin(username, password));
                break;
            }
            case "/changeInfo": {
                com.xshop.model.User user = new com.xshop.model.User();
                user.setID(Integer.parseInt(req.getParameter("ID")));
                user.setNickname(req.getParameter("nickname"));
                user.setAvatar(req.getParameter("avatar"));
                user.setSign(req.getParameter("sign"));
                user.setSite(req.getParameter("site"));
                user.setToken(req.getParameter("token"));
                json = gson.toJson(userService.changeUserInfo(user));
                break;
            }
            case "/changePassword": {
                int ID = Integer.parseInt(req.getParameter("ID"));
                String token = req.getParameter("token");
                String oldPassword = req.getParameter("oldPassword");
                String password = req.getParameter("password");
                json = gson.toJson(userService.changePassword(ID, token, oldPassword, password));
                break;
            }
            case "/getUserSite": {
                int userID = Integer.parseInt(req.getParameter("userID"));
                String token = req.getParameter("token");
                if (TokenUtil.verify(userID, token)) {
                    json = gson.toJson(userService.getUserSite(userID));
                } else {
                    json = gson.toJson(new Results(0, "非法访问"));
                }
                break;
            }
            default:
                json = gson.toJson(new Results(0, "未知请求"));
        }
        resp.getWriter().write(json);
    }
}
