package com.xshop.dao;

import com.xshop.model.Shop;
import com.xshop.model.ShopCart;
import com.xshop.model.User;
import com.xshop.util.DBUtil;
import com.xshop.util.MD5;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDao {

    public String getAdminPass(String username) {
        String sql = "select password from admin where username = ?";
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

    public List<User> getAllUser(Integer offset, Integer limit) {
        List<User> list = new ArrayList<>();
        String sql = "select * from user limit ? offset ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User u = new User();
                    u.setID(rs.getInt("ID"));
                    u.setUsername(rs.getString("username"));
                    u.setNickname(rs.getString("nickname"));
                    u.setAvatar(rs.getString("avatar"));
                    u.setSign(rs.getString("sign"));
                    u.setSite(rs.getString("site"));
                    u.setLastLogin(rs.getTimestamp("last_login"));
                    list.add(u);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Integer getUserCount() {
        String sql = "select count(username) from user";
        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void addUser(String username, String password, String nickname) {
        String sql = "insert into user(username, password, nickname, site) values(?, ?, ?, '中国')";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, MD5.crypt(password));
            ps.setString(3, nickname);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int userID) {
        String sql = "delete from user where ID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Integer getShopCount() {
        String sql = "select count(ID) from shop";
        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Shop> getAllShop(Integer offset, Integer limit) {
        List<Shop> list = new ArrayList<>();
        String sql = "select * from shop limit ? offset ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Shop s = new Shop();
                    s.setID(rs.getInt("ID"));
                    s.setName(rs.getString("name"));
                    s.setPrice(rs.getFloat("price"));
                    s.setOldPrice(rs.getFloat("old_price"));
                    s.setDescription(rs.getString("description"));
                    s.setImg(rs.getString("img"));
                    s.setSort(rs.getInt("sort"));
                    s.setOther(rs.getString("other"));
                    list.add(s);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addShop(Shop shop) {
        String sql = "insert into shop(ID, name, price, old_price, description, img, sort, other) values(?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, shop.getID());
            ps.setString(2, shop.getName());
            ps.setFloat(3, shop.getPrice());
            ps.setFloat(4, shop.getOldPrice());
            ps.setString(5, shop.getDescription());
            ps.setString(6, shop.getImg());
            ps.setInt(7, shop.getSort());
            ps.setString(8, shop.getOther());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeShopInform(Shop shop) {
        StringBuilder sql = new StringBuilder("update shop set ");
        boolean hasPrev = false;
        if (shop.getName() != null) { sql.append("name = ?"); hasPrev = true; }
        if (shop.getPrice() > 0) { if (hasPrev) sql.append(", "); sql.append("price = ?"); hasPrev = true; }
        if (shop.getOldPrice() > 0) { if (hasPrev) sql.append(", "); sql.append("old_price = ?"); hasPrev = true; }
        if (shop.getDescription() != null) { if (hasPrev) sql.append(", "); sql.append("description = ?"); hasPrev = true; }
        if (shop.getImg() != null) { if (hasPrev) sql.append(", "); sql.append("img = ?"); hasPrev = true; }
        if (shop.getSort() > 0) { if (hasPrev) sql.append(", "); sql.append("sort = ?"); hasPrev = true; }
        if (shop.getOther() != null) { if (hasPrev) sql.append(", "); sql.append("other = ?"); hasPrev = true; }
        sql.append(" where ID = ?");
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int idx = 1;
            if (shop.getName() != null) ps.setString(idx++, shop.getName());
            if (shop.getPrice() > 0) ps.setFloat(idx++, shop.getPrice());
            if (shop.getOldPrice() > 0) ps.setFloat(idx++, shop.getOldPrice());
            if (shop.getDescription() != null) ps.setString(idx++, shop.getDescription());
            if (shop.getImg() != null) ps.setString(idx++, shop.getImg());
            if (shop.getSort() > 0) ps.setInt(idx++, shop.getSort());
            if (shop.getOther() != null) ps.setString(idx++, shop.getOther());
            ps.setInt(idx, shop.getID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteShop(int shopID) {
        String sql = "delete from shop where ID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, shopID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Integer getCartCount() {
        String sql = "select count(user_id) from cart";
        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<ShopCart> getAllCart(Integer offset, Integer limit) {
        List<ShopCart> list = new ArrayList<>();
        String sql = "select cart.ID as cartId, cart.shop_id as shopId, shop.img, shop.name as shopName, " +
                     "shop.price, shop.old_price as oldPrice, cart.count, cart.user_id, u.nickname " +
                     "from cart " +
                     "LEFT JOIN shop on cart.shop_id = shop.ID " +
                     "LEFT JOIN user u on cart.user_id = u.ID " +
                     "limit ? offset ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ShopCart sc = new ShopCart();
                    sc.setCartId(rs.getInt("cartId"));
                    sc.setShopId(rs.getInt("shopId"));
                    sc.setImg(rs.getString("img"));
                    sc.setShopName(rs.getString("shopName"));
                    sc.setPrice(rs.getFloat("price"));
                    sc.setOldPrice(rs.getFloat("oldPrice"));
                    sc.setCount(rs.getInt("count"));
                    sc.setUserId(rs.getInt("user_id"));
                    sc.setNickname(rs.getString("nickname"));
                    list.add(sc);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void changePassword(String crypt) {
        String sql = "update admin set password = ? where username = 'admin'";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, crypt);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
