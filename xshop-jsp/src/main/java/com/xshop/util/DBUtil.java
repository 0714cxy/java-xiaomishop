package com.xshop.util;

import com.alibaba.druid.pool.DruidDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBUtil {

    private static DruidDataSource dataSource;

    static {
        dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/xiaomishop?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setInitialSize(5);
        dataSource.setMaxActive(20);
        dataSource.setMaxWait(60000);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void close(AutoCloseable... resources) {
        for (AutoCloseable r : resources) {
            if (r != null) {
                try {
                    r.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
