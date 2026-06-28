package com.xshop.web;

import com.xshop.model.Results;
import com.xshop.service.CartService;
import com.xshop.util.TokenUtil;
import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/v1/cart/*")
public class CartApiServlet extends HttpServlet {
    private CartService cartService = new CartService();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        String path = req.getPathInfo();
        String json;

        switch (path) {
            case "/getCart": {
                int userID = Integer.parseInt(req.getParameter("userID"));
                String token = req.getParameter("token");
                if (TokenUtil.verify(userID, token)) {
                    json = gson.toJson(cartService.getUserCartsByUserId(userID));
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        String path = req.getPathInfo();
        String json;

        switch (path) {
            case "/addCart": {
                int shopID = Integer.parseInt(req.getParameter("shopID"));
                int ID = Integer.parseInt(req.getParameter("ID"));
                String token = req.getParameter("token");
                int number = Integer.parseInt(req.getParameter("number"));
                json = gson.toJson(cartService.addCart(shopID, ID, token, number));
                break;
            }
            case "/deleteCart": {
                int cartID = Integer.parseInt(req.getParameter("cartID"));
                json = gson.toJson(cartService.deleteCart(cartID));
                break;
            }
            case "/changeCartNum": {
                int shopID = Integer.parseInt(req.getParameter("shopID"));
                int ID = Integer.parseInt(req.getParameter("ID"));
                String token = req.getParameter("token");
                int number = Integer.parseInt(req.getParameter("number"));
                if (TokenUtil.verify(ID, token)) {
                    json = gson.toJson(cartService.changeCartNum(shopID, ID, number));
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
