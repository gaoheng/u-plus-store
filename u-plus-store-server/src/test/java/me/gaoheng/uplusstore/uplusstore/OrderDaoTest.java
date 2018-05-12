package me.gaoheng.uplusstore.uplusstore;

import me.gaoheng.uplusstore.dao.OrderDao;
import me.gaoheng.uplusstore.model.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderDaoTest {

    @Autowired
    private OrderDao dao;

    @Test
    public void testInsert() {
        Order order = new Order();
        order.setTotal(new BigDecimal("99.99"));
        order.setDiscounted(new BigDecimal("6.66"));
        order.setActuallyPaid(new BigDecimal("93.33"));
        order.setCreateTime(new Date());
        int affectedRowCount = dao.insert(order);

        assertThat(affectedRowCount).isEqualTo(1);
        assertThat(order.getId()).isNotNull();
    }

    @Test
    public void testUpdate() {
        Order order = new Order();
        order.setId(1L);
        order.setTotal(new BigDecimal("199.99"));
        order.setDiscounted(new BigDecimal("6.66"));
        order.setActuallyPaid(new BigDecimal("193.33"));
        order.setCreateTime(new Date());
        int affectedRowCount = dao.update(order);

        assertThat(affectedRowCount).isEqualTo(1);
    }

    @Test
    public void testGet() {
        Long id = 1L;
        Order order = dao.get(id);

        assertThat(order).isNotNull();
        assertThat(order.getId()).isEqualTo(id);
    }

    @Test
    public void testDelete() {
        Long id = 1L;
        int affectedRowCount = dao.delete(id);
        assertThat(affectedRowCount).isEqualTo(1);
    }

}
