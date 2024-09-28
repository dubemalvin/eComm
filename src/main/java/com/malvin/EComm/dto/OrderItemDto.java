package com.malvin.EComm.dto;

import com.malvin.EComm.model.Order;
import com.malvin.EComm.model.Product;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private Long productId;
    private String productName;
    private String productBrand;
    private  int quantity;
    private BigDecimal price;
}
