package com.malvin.EComm.controller;

import com.malvin.EComm.exception.ResourceNotFoundException;
import com.malvin.EComm.model.Cart;
import com.malvin.EComm.response.ApiResponse;
import com.malvin.EComm.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final ICartService  cartService;

    @GetMapping("/{cartId}/my-cart")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId){
        try {
            Cart cart= cartService.getCart(cartId);
            return  ResponseEntity.ok(new ApiResponse("Success", cart));
        } catch (ResourceAccessException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId){
        try {
            cartService.clearCart(cartId);
            return  ResponseEntity.ok(new ApiResponse("Cart Cleared", null));
        } catch (ResourceAccessException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @GetMapping("/{cartId}/cart/total-price")
    public  ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long cartId){
        try {
            BigDecimal  totalPrice = cartService.getTotalPrice(cartId);
            return ResponseEntity.ok(new ApiResponse("TotalPrice", totalPrice));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
