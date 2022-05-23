package com.gmail.artemkrotenok.service;

import java.util.List;

import com.gmail.artemkrotenok.service.model.OrderDTO;
import com.gmail.artemkrotenok.service.model.OrderNewDTO;
import com.gmail.artemkrotenok.service.model.OrderStatusUpdateDTO;

public interface OrderService {

    OrderDTO add(OrderNewDTO orderNewDTO);

    Long getCountOrder();

    List<OrderDTO> getOrdersByPageSorted(Integer page);

    OrderDTO findById(Long id);

    OrderDTO changeOrderStatus(OrderStatusUpdateDTO orderStatusUpdateDTO);

    Long getCountOrderByUser(String userEmail);

    List<OrderDTO> getOrdersByPageSortedByUser(Integer page, String userEmail);

}
