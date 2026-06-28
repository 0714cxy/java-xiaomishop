package com.xshop.service;

import com.xshop.dao.ShopDao;
import com.xshop.model.Carousel;
import com.xshop.model.Results;
import com.xshop.model.Shop;
import java.util.List;

public class ShopService {
    private ShopDao shopDao = new ShopDao();

    public Results getShopInfo(int shopID) {
        return new Results(1, "获取成功", shopDao.getShopInfoById(shopID));
    }

    public Results getAllShop() {
        List<Shop> allShop = shopDao.getAllShop();
        if (allShop.isEmpty()) {
            return new Results(0, "获取失败");
        }
        return new Results(1, "获取成功", allShop);
    }

    public Results getShopList(int type) {
        List<Shop> shops = shopDao.getShopList(type);
        List<Carousel> carousels = shopDao.getCarouselList(type);
        StringBuilder carousel = new StringBuilder();
        for (int i = 0; i < carousels.size(); i++) {
            if (i == 0) {
                carousel.append(carousels.get(0).getImg());
            } else {
                carousel.append("&&").append(carousels.get(i).getImg());
            }
        }
        if (shops.isEmpty()) {
            return new Results(0, "获取失败");
        }
        return new Results(1, "获取成功", shops, carousel.toString());
    }

    public Results searchShop(String key) {
        return new Results(1, "获取成功", shopDao.searchShopByShopName(key));
    }
}
