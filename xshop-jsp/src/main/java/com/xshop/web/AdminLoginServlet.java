package com.xshop.web;

import com.xshop.model.Results;
import com.xshop.service.AdminService;
import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/login")
public class AdminLoginServlet extends HttpServlet {
    private AdminService adminService = new AdminService();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Results result = adminService.adminLogin(username, password);
        if (result.getCode() == 1) {
            HttpSession session = req.getSession();
            session.setAttribute("admin", username);
        }
        resp.getWriter().write(gson.toJson(result));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("admin") != null) {
            resp.sendRedirect(req.getContextPath() + "/admin/index.jsp");
        } else {
            req.getRequestDispatcher("/admin/login.jsp").forward(req, resp);
        }
    }
}
