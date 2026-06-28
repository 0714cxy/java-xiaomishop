package com.xshop.dao;

import com.xshop.model.User;
import com.xshop.util.DBUtil;
import com.xshop.util.MD5;
import java.sql.*;

public class UserDao {

    public User getUserById(int id) {
        String sql = "select * from user where id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getUserByUsername(String username) {
        String sql = "select count(*) from user where username = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int registerUser(String username, String password, String nickname) {
        String sql = "insert into user(username, password, nickname, site) values(?, ?, ?, '中国')";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, MD5.crypt(password));
            ps.setString(3, nickname);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getUserPassByUsername(String username) {
        String sql = "select password from user where username = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertToken(String username, String token) {
        String sql = "update user set token = ? where username = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, token);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getUserIdByUsername(String username) {
        String sql = "select ID from user where username = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getTokenByUserId(int ID) {
        String sql = "select token from user where ID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("token");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertLoginTime(String username) {
        String sql = "update user set now_login = now() where username = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserInfoById(int userID) {
        String sql = "select username, nickname, avatar, site, sign, last_login from user where ID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setUsername(rs.getString("username"));
                    u.setNickname(rs.getString("nickname"));
                    u.setAvatar(rs.getString("avatar"));
                    u.setSite(rs.getString("site"));
                    u.setSign(rs.getString("sign"));
                    u.setLastLogin(rs.getTimestamp("last_login"));
                    return u;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void changeUserInfo(User user) {
        StringBuilder sql = new StringBuilder("update user set ");
        boolean hasPrev = false;
        if (user.getNickname() != null) { sql.append("nickname = ?"); hasPrev = true; }
        if (user.getAvatar() != null) { if (hasPrev) sql.append(", "); sql.append("avatar = ?"); hasPrev = true; }
        if (user.getSite() != null) { if (hasPrev) sql.append(", "); sql.append("site = ?"); hasPrev = true; }
        if (user.getSign() != null) { if (hasPrev) sql.append(", "); sql.append("sign = ?"); hasPrev = true; }
        sql.append(" where ID = ?");
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int idx = 1;
            if (user.getNickname() != null) ps.setString(idx++, user.getNickname());
            if (user.getAvatar() != null) ps.setString(idx++, user.getAvatar());
            if (user.getSite() != null) ps.setString(idx++, user.getSite());
            if (user.getSign() != null) ps.setString(idx++, user.getSign());
            ps.setInt(idx, user.getID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changePassword(int id, String crypt) {
        String sql = "update user set password = ? where ID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, crypt);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAvatar(int userID, String avatarName) {
        String sql = "update user set avatar = ? where ID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, avatarName);
            ps.setInt(2, userID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getUserSite(int userID) {
        String sql = "select site from user where ID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("site");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private User mapUser(ResultSet rs) throws SQLException {
        User u = new User();
        u.setID(rs.getInt("ID"));
        u.setUsername(rs.getString("username"));
        u.setNickname(rs.getString("nickname"));
        u.setPassword(rs.getString("password"));
        u.setAvatar(rs.getString("avatar"));
        u.setSign(rs.getString("sign"));
        u.setSite(rs.getString("site"));
        u.setLastLogin(rs.getTimestamp("last_login"));
        u.setNowLogin(rs.getTimestamp("now_login"));
        u.setToken(rs.getString("token"));
        return u;
    }
}
