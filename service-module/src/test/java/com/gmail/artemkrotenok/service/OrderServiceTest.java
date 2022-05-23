package com.gmail.artemkrotenok.service;

import java.math.BigDecimal;

import com.gmail.artemkrotenok.repository.ItemRepository;
import com.gmail.artemkrotenok.repository.OrderRepository;
import com.gmail.artemkrotenok.repository.UserRepository;
import com.gmail.artemkrotenok.repository.model.Item;
import com.gmail.artemkrotenok.repository.model.Order;
import com.gmail.artemkrotenok.repository.model.User;
import com.gmail.artemkrotenok.repository.model.UserInformation;
import com.gmail.artemkrotenok.service.impl.OrderServiceImpl;
import com.gmail.artemkrotenok.service.model.OrderDTO;
import com.gmail.artemkrotenok.service.model.OrderNewDTO;
import com.gmail.artemkrotenok.service.util.OrderConverterUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    public static final long TEST_ORDER_ID = 2L;
    private static final Integer TEST_ITEM_QUANTITY = 5;
    private static final BigDecimal TEST_ORDER_AMOUNT = BigDecimal.valueOf(615);
    private static final BigDecimal TEST_ITEM_PRICE = BigDecimal.valueOf(123);
    private static final long TEST_ITEM_ID = 3L;
    private static final String TEST_USER_EMAIL = "test@test.com";

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ItemRepository itemRepository;

    private OrderService orderService;

    @BeforeEach
    public void setup() {
        this.orderService = new OrderServiceImpl(orderRepository, userRepository, itemRepository);
    }

    @Test
    public void add_returnOrderDTO() {
        OrderDTO orderDTO = getValidOrderDTO();
        User user = new User();
        user.setUserInformation(new UserInformation());
        when(userRepository.getUserByEmail(TEST_USER_EMAIL)).thenReturn(user);
        Item item = new Item();
        item.setPrice(TEST_ITEM_PRICE);
        when(itemRepository.findById(TEST_ITEM_ID)).thenReturn(item);
        doNothing().when(orderRepository).persist(any(Order.class));
        OrderDTO addedOrderDTO = orderService.add(getValidOrderNewDTO());
        Assertions.assertThat(addedOrderDTO).isNotNull();
        Assertions.assertThat(addedOrderDTO.getAmount()).isEqualTo(orderDTO.getAmount());
        verify(orderRepository, times(1)).persist(any(Order.class));
    }

    @Test
    public void getOrderById_returnOrderDTO() {
        Order order = getEmptyOrder();
        when(orderRepository.findById(TEST_ORDER_ID)).thenReturn(order);
        OrderDTO orderDTO = orderService.findById(TEST_ORDER_ID);
        verify(orderRepository, times(1)).findById(TEST_ORDER_ID);
        Assertions.assertThat(orderDTO).isNotNull();
    }

    @Test
    public void getOrderById_returnNull() {
        when(orderRepository.findById(TEST_ORDER_ID)).thenReturn(null);
        OrderDTO orderDTO = orderService.findById(TEST_ORDER_ID);
        verify(orderRepository, times(1)).findById(TEST_ORDER_ID);
        Assertions.assertThat(orderDTO).isNull();
    }

    private Order getEmptyOrder() {
        Order order = new Order();
        order.setItem(new Item());
        User user = new User();
        user.setUserInformation(new UserInformation());
        order.setUser(user);
        return order;
    }

    private Order getValidOrder() {
        Order order = getEmptyOrder();
        order.setAmount(TEST_ORDER_AMOUNT);
        return order;
    }

    private OrderDTO getValidOrderDTO() {
        return OrderConverterUtil.getDTOFromObject(getValidOrder());
    }

    private OrderNewDTO getValidOrderNewDTO() {
        OrderNewDTO orderNewDTO = new OrderNewDTO();
        orderNewDTO.setUserEmail(TEST_USER_EMAIL);
        orderNewDTO.setItemId(TEST_ITEM_ID);
        orderNewDTO.setQuantity(TEST_ITEM_QUANTITY);
        return orderNewDTO;
    }

}
