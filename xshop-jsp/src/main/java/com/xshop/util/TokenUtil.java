package com.xshop.util;

import com.xshop.dao.UserDao;

public class TokenUtil {
    private static UserDao userDao = new UserDao();

    public static boolean verify(int ID, String token) {
        String userToken = userDao.getTokenByUserId(ID);
        return token != null && token.equals(userToken);
    }
}
