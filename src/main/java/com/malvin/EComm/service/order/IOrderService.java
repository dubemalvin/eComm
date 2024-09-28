package com.malvin.EComm.service.order;

import com.malvin.EComm.dto.OrderDto;
import com.malvin.EComm.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);

    OrderDto convertDto(Order order);
}
