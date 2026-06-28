package com.xshop.model;

public class Cart {
    private int id;
    private int userId;
    private int shopId;
    private int count;
    private int collectType;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getID() { return id; }
    public void setID(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getShopId() { return shopId; }
    public void setShopId(int shopId) { this.shopId = shopId; }
    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }
    public int getCollectType() { return collectType; }
    public void setCollectType(int collectType) { this.collectType = collectType; }
}
