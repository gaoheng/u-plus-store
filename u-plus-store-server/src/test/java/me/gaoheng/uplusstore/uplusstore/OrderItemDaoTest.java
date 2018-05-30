package me.gaoheng.uplusstore.uplusstore;

import me.gaoheng.uplusstore.dao.OrderItemDao;
import me.gaoheng.uplusstore.model.OrderItem;
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
public class OrderItemDaoTest {

    @Autowired
    private OrderItemDao dao;

    @Test
    public void testInsert() {
        Long orderId = 1L;
        Long skuId = 4L;
        OrderItem item = new OrderItem();
        item.setOrderId(orderId);
        item.setSkuId(skuId);
        item.setSkuName("test-name");
        item.setSkuCode("test-code");
        item.setSkuColor("RED");
        item.setSkuSize("XXXXL");
        item.setSkuPrice(new BigDecimal("99.99"));
        item.setQuantity(99);
        item.setCreateTime(new Date());
        int affectedRowCount = dao.insert(item);

        assertThat(affectedRowCount).isEqualTo(1);
        assertThat(item.getId()).isNotNull();
    }

    @Test
    public void testGet() {
        Long id = 1L;
        OrderItem item = dao.get(id);

        assertThat(item).isNotNull();
        assertThat(item.getId()).isEqualTo(id);
    }

    @Test
    public void testDelete() {
        Long id = 1L;
        int affectedRowCount = dao.delete(id);
        assertThat(affectedRowCount).isEqualTo(1);
    }

}
