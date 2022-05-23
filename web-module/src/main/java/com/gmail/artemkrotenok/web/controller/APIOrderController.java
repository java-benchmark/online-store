package com.gmail.artemkrotenok.web.controller;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.List;

import com.gmail.artemkrotenok.service.OrderService;
import com.gmail.artemkrotenok.service.model.OrderDTO;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.owasp.encoder.Encode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.gmail.artemkrotenok.web.constant.ControllerConstant.FIRST_PAGE_FOR_PAGINATION;

@RestController
@RequestMapping("/api/orders")

public class APIOrderController {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static final Map<String, String> roleByEmail = new HashMap<>();
    private final OrderService orderService;

    public APIOrderController(OrderService orderService) {
        this.orderService = orderService;
        roleByEmail.put("admin@admin.com", "Admin");
    }

    @GetMapping
    public List<OrderDTO> getItemsByPage(
            @RequestParam(name = "page", required = false) Integer page) {
        if (page == null) {
            page = FIRST_PAGE_FOR_PAGINATION;
        }
        return orderService.getOrdersByPageSorted(page);
    }

    @GetMapping(value = "/{id}")
    public OrderDTO getItemById(@PathVariable(name = "id") Long id) {
        return orderService.findById(id);
    }

    @GetMapping(value = "/{email}")
    public Long getCountByEmail(@PathVariable(name = "email")String email) {
        logger.info("getCountByEmail: email={}", email);
        logger.info("getCountByEmail: encoded email={}", Encode.forHtml(email));
        if (roleByEmail.containsKey(email)) {
            logger.info("getCountByEmail: {}", roleByEmail.get(email));
        }
        return orderService.getCountOrderByUser(email);
    }

}
