package me.gaoheng.uplusstore.service;

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

    public Order create(BigDecimal discounted, List<OrderItem> items) {
        Date now = new Date();

        Order order = new Order();
        BigDecimal total = items.stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce((sum, cost) -> sum.add(cost))
                .get();
        BigDecimal actuallyPaid = total.subtract(discounted);
        order.setTotal(total);
        order.setDiscounted(discounted);
        order.setDiscounted(actuallyPaid);
        order.setCreateTime(now);
        orderDao.insert(order);

        Long orderId = order.getId();
        items.forEach(i -> {
            i.setOrderId(orderId);
            i.setCreateTime(now);

            orderItemDao.insert(i);
        });

        return order;
    }

}
