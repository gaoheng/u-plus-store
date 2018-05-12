package me.gaoheng.uplusstore.controller;

import lombok.Data;
import me.gaoheng.uplusstore.model.Order;
import me.gaoheng.uplusstore.model.OrderItem;
import me.gaoheng.uplusstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(path = "/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.POST)
    public Order create(@RequestBody OrderCreating orderCreating) {
        Order order = orderService.create(orderCreating.getDiscounted(), orderCreating.getItems());
        return order;
    }

    @Data
    public static class OrderCreating implements Serializable {
        private static final long serialVersionUID = -8256023313021161128L;

        private BigDecimal discounted;

        private List<OrderItem> items;
    }

}
