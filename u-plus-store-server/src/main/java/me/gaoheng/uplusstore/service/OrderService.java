package me.gaoheng.uplusstore.service;

import me.gaoheng.uplusstore.controller.OrderController;
import me.gaoheng.uplusstore.dao.OrderDao;
import me.gaoheng.uplusstore.dao.OrderItemDao;
import me.gaoheng.uplusstore.model.Order;
import me.gaoheng.uplusstore.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderItemDao orderItemDao;

    public Order create(OrderController.OrderCreating orderCreating) {
        Date now = new Date();

        Order order = new Order();
        BigDecimal total = orderCreating.getItems().stream()
                .map(i -> i.getSkuPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce((sum, cost) -> sum.add(cost))
                .get();
        BigDecimal paid = total.subtract(orderCreating.getDiscount());

        if (orderCreating.getTotal().compareTo(total) != 0) {
            throw new IllegalArgumentException("订单总金额错误");
        }
        if (orderCreating.getPaid().compareTo(paid) != 0) {
            throw new IllegalArgumentException("订单实付金额错误");
        }

        order.setTotal(total);
        order.setDiscount(orderCreating.getDiscount());
        order.setPaid(paid);
        order.setCreateTime(now);
        orderDao.insert(order);

        Long orderId = order.getId();
        orderCreating.getItems().forEach(i -> {
            OrderItem item = new OrderItem();
            item.setOrderId(orderId);
            item.setCreateTime(now);
            item.setSkuId(i.getSkuId());
            item.setSkuCode(i.getSkuCode());
            item.setSkuName(i.getSkuName());
            item.setSkuColor(i.getSkuColor());
            item.setSkuSize(i.getSkuSize());
            item.setSkuPrice(i.getSkuPrice());
            item.setQuantity(i.getQuantity());

            orderItemDao.insert(item);
        });

        return order;
    }

}
