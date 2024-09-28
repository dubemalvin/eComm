package com.malvin.EComm.service.cart;

import com.malvin.EComm.model.Cart;
import com.malvin.EComm.model.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Cart initializerNewCart(User user);

    Cart getCartByUserId(Long userId);
}
