package com.code.ecommerce.service.impl;

import com.code.ecommerce.dto.request.ManageOrderStatus;
import com.code.ecommerce.dto.request.OrderItemRequest;
import com.code.ecommerce.dto.request.OrderRequest;
import com.code.ecommerce.dto.request.UpdateOrderRequest;
import com.code.ecommerce.dto.response.CountOrderByStatusResponse;
import com.code.ecommerce.dto.response.OrderDto;
import com.code.ecommerce.dto.response.PagingData;
import com.code.ecommerce.entity.DeliveryStatus;
import com.code.ecommerce.entity.Order;
import com.code.ecommerce.entity.OrderItem;
import com.code.ecommerce.entity.Status;
import com.code.ecommerce.exceptions.MissingInputException;
import com.code.ecommerce.exceptions.NotFoundException;
import com.code.ecommerce.mapper.OrderItemMapper;
import com.code.ecommerce.mapper.OrderMapper;
import com.code.ecommerce.repository.OrderRepository;
import com.code.ecommerce.service.OrderService;
import com.code.ecommerce.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.code.ecommerce.utils.JwtUtils.getTokenFromBearer;



@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public String createOrder(OrderRequest orderRequest) {
        Order orderEntity = orderMapper.reqToEntity(orderRequest);

        List<OrderItem> orderItem = orderItemMapper.toEntity(orderRequest.getOrderItemRequest());

        orderEntity.setOrderItems(orderItem);
        orderEntity.setOrderDate(new Date());
        orderEntity.setDeliveryStatus(DeliveryStatus.PENDING);
        orderEntity.setStatus(Status.PENDING);

        return orderMapper.toDto(orderRepository.save(orderEntity)).getId();
    }

    @Override
    public OrderDto findOrderById(String id) {


        return orderMapper.toDto(orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Can't find order with id" + id)));
    }

    @Override
    public PagingData getOrders(Integer offset, Integer pageSize) {

        Page<Order> orderPageEntity;
        Pageable pageable = PageRequest.of(offset, pageSize);

        orderPageEntity = orderRepository.findAll(pageable);

        Page<OrderDto> categoryDTOPage = orderPageEntity.map(orderMapper::toDto);

        return PagingData.builder()
                .data(categoryDTOPage)
                .offset(offset)
                .pageSize(pageSize)
                .totalRecord(categoryDTOPage.getTotalElements())
                .build();
    }

    @Override
    @Transactional
    public OrderDto changeStatusEvent(UpdateOrderRequest updateOrderRequest) {
        log.info("Got message <{}>", updateOrderRequest.toString());
//        Order order = orderRepository.findById(updateOrderRequest.getOrderId())
//                .orElseThrow(() -> new NotFoundException("Can't find order with id" + updateOrderRequest.getOrderId()));
//
//        order.setDeliveryStatus(DeliveryStatus.valueOf(updateOrderRequest.getDeliveryStatus()));
//        order.setPaymentId(updateOrderRequest.getPaymentId());
//        return orderMapper.toDto(orderRepository.save(order));
        return null;
    }

    @Override
    @Transactional
    public OrderDto update(ManageOrderStatus manageOrderStatus, String id) {
        Order existingOrder = orderRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Can't find order with id" + id));

//        fields.forEach((key, value) -> {
//            // Tìm tên của trường dựa vào "key"
//            Field field = ReflectionUtils.findField(Order.class, key);
//            if (field == null) throw new NullPointerException("Can't find any field");
//            // Set quyền truy cập vào biến kể cả nó là private
//            field.setAccessible(true);
//            // đặt giá trị cho một field cụ thể trong một đối tượng dựa trên tên của field đó
//            ReflectionUtils.setField(field, existingOrder, value);
//        });
        existingOrder.setDeliveryStatus(DeliveryStatus.valueOf(manageOrderStatus.getDeliveryStatus()));
        existingOrder.setStatus(Status.valueOf(manageOrderStatus.getStatus()));
        orderRepository.save(existingOrder);
        return orderMapper.toDto(existingOrder);
    }

    @Override
    public String cancelOrder(String id) {
        orderRepository.deleteById(id);
        return id;
    }

    @Override
    public List<CountOrderByStatusResponse> getAllByStatus() {
        List<CountOrderByStatusResponse> responses = new ArrayList<>();
        for (Status status : Status.values()) {
            Integer countStatusOrder = orderRepository.countOrderByStatus(Status.valueOf(status.name()));
            CountOrderByStatusResponse statusOrder = new CountOrderByStatusResponse(status, countStatusOrder);
            responses.add(statusOrder);
        }

        return responses;
    }

    @Override
    public List<OrderDto> getOrderOfCurrentUser(String token) {
        if (token == null) {
            throw new MissingInputException("Missing input id");
        }

        Claims claims = JwtUtils.parseClaims(getTokenFromBearer(token));
        String userId = (String) claims.get("userId");
        return orderMapper.toDto(orderRepository.findByUserId(userId));
    }

    @Override
    public List<OrderDto> getAllOrder() {
        return orderMapper.toDto(orderRepository.findAll());
    }

    private double getTotalPrice(Set<OrderItemRequest> orderItemDtos, String token) {

//        double totalPrice = 0.0;
//        for (OrderItemRequest item : orderItemDtos) {
//
//            ResponseMessage productResponse = productClient.findById(token, item.getProductId()).getBody();
//            assert productResponse != null;
//            if (productResponse.getData() != null) {
//                ProductDto productDto = objectMapper.convertValue(productResponse.getData(), ProductDto.class);
//
//                totalPrice += productDto.getPriceUnit() * productDto.getQuantity();
//            }
//
//        }
//        return totalPrice;
        return 2.5;
    }

}
