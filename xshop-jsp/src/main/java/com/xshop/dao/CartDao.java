package com.xshop.dao;

import com.xshop.model.ShopCart;
import com.xshop.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDao {

    public List<ShopCart> getCartsByUserID(int id) {
        List<ShopCart> list = new ArrayList<>();
        String sql = "select t1.ID as cart_id, t2.ID as shop_id, t2.img, t2.name as shop_name, t2.price, t1.count " +
                     "from cart t1 INNER JOIN shop t2 on t1.shop_id = t2.ID where t1.user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ShopCart sc = new ShopCart();
                    sc.setCartId(rs.getInt("cart_id"));
                    sc.setShopId(rs.getInt("shop_id"));
                    sc.setImg(rs.getString("img"));
                    sc.setShopName(rs.getString("shop_name"));
                    sc.setPrice(rs.getFloat("price"));
                    sc.setCount(rs.getInt("count"));
                    list.add(sc);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void changeShopNum(int id, int count) {
        String sql = "update cart set count = ? where ID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, count);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCart(int shopID, int id, int number) {
        String sql = "insert into cart(user_id, shop_id, count) values(?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, shopID);
            ps.setInt(3, number);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCartByC(int cartID) {
        String sql = "delete from cart where ID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeCartNum(int shopID, int id, int number) {
        String sql = "update cart set count = ? where shop_id = ? and user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, number);
            ps.setInt(2, shopID);
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
