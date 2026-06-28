package com.xshop.model;

public class Shop {
    private int id;
    private String name;
    private float price;
    private float oldPrice;
    private String description;
    private String img;
    private int sort;
    private String other;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getID() { return id; }
    public void setID(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }
    public float getOldPrice() { return oldPrice; }
    public void setOldPrice(float oldPrice) { this.oldPrice = oldPrice; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImg() { return img; }
    public void setImg(String img) { this.img = img; }
    public int getSort() { return sort; }
    public void setSort(int sort) { this.sort = sort; }
    public String getOther() { return other; }
    public void setOther(String other) { this.other = other; }
}
