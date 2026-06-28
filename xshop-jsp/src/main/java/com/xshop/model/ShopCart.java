package com.xshop.model;

public class ShopCart {
    private int cartId;
    private int shopId;
    private String img;
    private String shopName;
    private float price;
    private float oldPrice;
    private int count;
    private String nickname;
    private int userId;

    public int getCartId() { return cartId; }
    public void setCartId(int cartId) { this.cartId = cartId; }
    public int getShopId() { return shopId; }
    public void setShopId(int shopId) { this.shopId = shopId; }
    public String getImg() { return img; }
    public void setImg(String img) { this.img = img; }
    public String getShopName() { return shopName; }
    public void setShopName(String shopName) { this.shopName = shopName; }
    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }
    public float getOldPrice() { return oldPrice; }
    public void setOldPrice(float oldPrice) { this.oldPrice = oldPrice; }
    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}
