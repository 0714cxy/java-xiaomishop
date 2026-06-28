package com.xshop.service;

import com.xshop.dao.CartDao;
import com.xshop.model.Results;
import com.xshop.model.ShopCart;
import com.xshop.util.TokenUtil;
import java.util.List;

public class CartService {
    private CartDao cartDao = new CartDao();

    public Results addCart(int shopID, int id, String token, int number) {
        if (!TokenUtil.verify(id, token)) {
            return new Results(0, "非法访问");
        }
        int flag = 0;
        List<ShopCart> userCarts = cartDao.getCartsByUserID(id);
        for (ShopCart cart : userCarts) {
            if (cart.getShopId() == shopID) {
                int newCount = number == 1 ? (cart.getCount() + 1) : (cart.getCount() - 1);
                cartDao.changeShopNum(cart.getCartId(), newCount);
                flag = 1;
                break;
            }
        }
        if (flag == 1) {
            return new Results(1, "修改商品数量成功");
        } else {
            cartDao.addCart(shopID, id, number);
            return new Results(1, "添加成功");
        }
    }

    public Results getUserCartsByUserId(int userID) {
        return new Results(1, "获取成功", cartDao.getCartsByUserID(userID));
    }

    public Results deleteCart(int cartID) {
        cartDao.deleteCartByC(cartID);
        return new Results(1, "删除成功");
    }

    public Results changeCartNum(int shopID, int id, int number) {
        cartDao.changeCartNum(shopID, id, number);
        return new Results(1, "修改商品数量成功");
    }
}
