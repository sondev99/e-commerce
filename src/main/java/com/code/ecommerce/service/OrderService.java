package com.code.ecommerce.service;



import com.code.ecommerce.dto.request.ManageOrderStatus;
import com.code.ecommerce.dto.request.OrderRequest;
import com.code.ecommerce.dto.request.UpdateOrderRequest;
import com.code.ecommerce.dto.response.CountOrderByStatusResponse;
import com.code.ecommerce.dto.response.OrderDto;
import com.code.ecommerce.dto.response.PagingData;

import java.util.List;

public interface OrderService {

    String createOrder(OrderRequest orderRequest);

    OrderDto findOrderById(String id);

    PagingData getOrders(Integer offset, Integer pageSize);

    OrderDto changeStatusEvent(UpdateOrderRequest updateOrderRequest);

    OrderDto update(ManageOrderStatus manageOrderStatus, String id);

    String cancelOrder(String id);

    List<CountOrderByStatusResponse> getAllByStatus();

    List<OrderDto> getOrderOfCurrentUser(String token);

    List<OrderDto> getAllOrder();
}
