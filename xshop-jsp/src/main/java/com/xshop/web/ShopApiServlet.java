package com.xshop.web;

import com.xshop.model.Results;
import com.xshop.service.ShopService;
import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/v1/shop/*")
public class ShopApiServlet extends HttpServlet {
    private ShopService shopService = new ShopService();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        String path = req.getPathInfo();
        String json;

        switch (path) {
            case "/getInfo": {
                int shopID = Integer.parseInt(req.getParameter("shopID"));
                json = gson.toJson(shopService.getShopInfo(shopID));
                break;
            }
            case "/getAllShop": {
                json = gson.toJson(shopService.getAllShop());
                break;
            }
            case "/getShopList": {
                int type = Integer.parseInt(req.getParameter("type"));
                json = gson.toJson(shopService.getShopList(type));
                break;
            }
            case "/search": {
                String key = req.getParameter("key");
                json = gson.toJson(shopService.searchShop(key));
                break;
            }
            default:
                json = gson.toJson(new Results(0, "未知请求"));
        }
        resp.getWriter().write(json);
    }
}
