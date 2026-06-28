package com.xshop.web;

import com.xshop.dao.UserDao;
import com.xshop.model.Results;
import com.xshop.util.TokenUtil;
import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/api/v1/user/addAvatar")
@MultipartConfig
public class PicUploadServlet extends HttpServlet {
    private Gson gson = new Gson();
    private UserDao userDao = new UserDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");

        int userID = Integer.parseInt(req.getParameter("userID"));
        String token = req.getParameter("token");

        if (!TokenUtil.verify(userID, token)) {
            resp.getWriter().write(gson.toJson(new Results(0, "非法访问")));
            return;
        }

        Part part = req.getPart("image");
        if (part == null || part.getSize() == 0) {
            resp.getWriter().write(gson.toJson(new Results(0, "未选择文件")));
            return;
        }

        String uploadPath = getServletContext().getRealPath("/img");
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        String ext = ".jpg";
        String submittedFileName = part.getSubmittedFileName();
        if (submittedFileName != null) {
            int dot = submittedFileName.lastIndexOf('.');
            if (dot >= 0) ext = submittedFileName.substring(dot);
        }

        String fileName = UUID.randomUUID().toString().replace("-", "") + ext;
        part.write(uploadPath + File.separator + fileName);

        String avatarName = fileName.substring(0, fileName.lastIndexOf('.'));
        userDao.updateAvatar(userID, avatarName);

        resp.getWriter().write(gson.toJson(new Results(1, "上传成功")));
    }
}
