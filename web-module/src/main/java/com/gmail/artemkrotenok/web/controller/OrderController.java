package com.gmail.artemkrotenok.web.controller;

import java.util.Arrays;
import java.util.List;

import com.gmail.artemkrotenok.repository.model.OrderStatusEnum;
import com.gmail.artemkrotenok.repository.model.UserRoleEnum;
import com.gmail.artemkrotenok.service.OrderService;
import com.gmail.artemkrotenok.service.UserService;
import com.gmail.artemkrotenok.service.model.OrderDTO;
import com.gmail.artemkrotenok.service.model.OrderNewDTO;
import com.gmail.artemkrotenok.service.model.OrderStatusUpdateDTO;
import com.gmail.artemkrotenok.service.model.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.gmail.artemkrotenok.web.constant.ControllerConstant.FIRST_PAGE_FOR_PAGINATION;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping
    public String getOrdersPage(
            @RequestParam(name = "page", required = false) Integer page,
            Model model
    ) {
        if (page == null) {
            page = FIRST_PAGE_FOR_PAGINATION;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        UserDTO userDTO = userService.getUserByEmail(userEmail);

        List<OrderDTO> ordersDTO;
        if (userDTO.getRole().equals(UserRoleEnum.CUSTOMER_USER)) {
            Long countOrders = orderService.getCountOrderByUser(userEmail);
            model.addAttribute("countOrders", countOrders);
            model.addAttribute("page", page);
            ordersDTO = orderService.getOrdersByPageSortedByUser(page, userEmail);
        } else {
            Long countOrders = orderService.getCountOrder();
            model.addAttribute("countOrders", countOrders);
            model.addAttribute("page", page);
            ordersDTO = orderService.getOrdersByPageSorted(page);
        }
        model.addAttribute("orders", ordersDTO);
        return "orders";
    }

    @PostMapping
    public String addOrder(
            Model model,
            @ModelAttribute(name = "orderNewDTO") OrderNewDTO orderNewDTO
    ) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        orderNewDTO.setUserEmail(authentication.getName());
        if (orderService.add(orderNewDTO) != null) {
            model.addAttribute("message", "Order was added successfully");
        } else {
            model.addAttribute("message", "Order not was added");
        }
        model.addAttribute("redirect", "/items");
        return "message";
    }

    @GetMapping("/{id}")
    public String getOrderPage(@PathVariable Long id, Model model) {
        OrderDTO orderDTO = orderService.findById(id);
        if (orderDTO == null) {
            model.addAttribute("message", "Order not found for id='" + id + "'");
            model.addAttribute("redirect", "/orders");
            return "message";
        }
        model.addAttribute("statusList", Arrays.asList(OrderStatusEnum.values()));
        model.addAttribute("order", orderDTO);
        return "order";
    }

    @PostMapping("/update")
    public String updateUser(
            @ModelAttribute(name = "orderStatusUpdate") OrderStatusUpdateDTO orderStatusUpdate,
            Model model
    ) {
        model.addAttribute("redirect", "/orders");
        if (orderService.changeOrderStatus(orderStatusUpdate) == null) {
            model.addAttribute("message", "Error selected order, change not saved");
            return "message";
        }
        model.addAttribute("message", "Change status order was success saved");
        return "message";
    }

}
