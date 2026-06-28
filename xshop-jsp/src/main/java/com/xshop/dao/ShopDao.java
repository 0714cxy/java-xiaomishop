package com.xshop.dao;

import com.xshop.model.Shop;
import com.xshop.model.Carousel;
import com.xshop.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShopDao {

    public Shop getShopInfoById(int shopID) {
        String sql = "select * from shop where ID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, shopID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapShop(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Shop> getAllShop() {
        List<Shop> list = new ArrayList<>();
        String sql = "select * from shop";
        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapShop(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Shop> getShopList(int type) {
        List<Shop> list = new ArrayList<>();
        String sql = "select * from shop where sort = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, type);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapShop(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Shop> searchShopByShopName(String key) {
        List<Shop> list = new ArrayList<>();
        String sql = "select * from shop where name like ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + key + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapShop(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Carousel> getCarouselList(int type) {
        List<Carousel> list = new ArrayList<>();
        String sql = "select * from carousel where sort = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, type);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Carousel c = new Carousel();
                    c.setID(rs.getInt("ID"));
                    c.setSort(rs.getInt("sort"));
                    c.setImg(rs.getString("img"));
                    c.setShopId(rs.getInt("shop_id"));
                    list.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Shop mapShop(ResultSet rs) throws SQLException {
        Shop s = new Shop();
        s.setID(rs.getInt("ID"));
        s.setName(rs.getString("name"));
        s.setPrice(rs.getFloat("price"));
        s.setOldPrice(rs.getFloat("old_price"));
        s.setDescription(rs.getString("description"));
        s.setImg(rs.getString("img"));
        s.setSort(rs.getInt("sort"));
        s.setOther(rs.getString("other"));
        return s;
    }
}
