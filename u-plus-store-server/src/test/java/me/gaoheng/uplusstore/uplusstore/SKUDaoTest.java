package me.gaoheng.uplusstore.uplusstore;

import me.gaoheng.uplusstore.dao.SKUDao;
import me.gaoheng.uplusstore.model.SKU;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SKUDaoTest {

    @Autowired
    private SKUDao dao;

    @Test
    public void testInsert() {
        SKU sku = new SKU();
        sku.setName("Test SKU");
        sku.setCode("test");
        sku.setColor("black");
        sku.setSize("XL");
        sku.setStock(100);
        sku.setMainImg("http://main.jpg");
        sku.setTagImg("http://tag.jpg");
        int affectedRowCount = dao.insert(sku);

        assertThat(affectedRowCount).isEqualTo(1);
        assertThat(sku.getId()).isNotNull();
    }

    @Test
    public void testUpdate() {
        SKU sku = new SKU();
        sku.setId(4L);
        sku.setName("Test SKU1");
        sku.setCode("test1");
        sku.setColor("black1");
        sku.setSize("XL1");
        sku.setStock(99);
        sku.setMainImg("http://main.jpg");
        sku.setTagImg("http://tag.jpg");
        int affectedRowCount = dao.update(sku);

        assertThat(affectedRowCount).isEqualTo(1);
    }

    @Test
    public void testGet() {
        Long id = 4L;
        SKU sku = dao.get(id);

        assertThat(sku).isNotNull();
        assertThat(sku.getId()).isEqualTo(id);
    }

    @Test
    public void testGetByCode() {
        String code = "2018050700000";
        SKU sku = dao.getByCode(code);

        assertThat(sku).isNotNull();
        assertThat(sku.getCode()).isEqualTo(code);
    }

    @Test
    public void testDelete() {
        Long id = 4L;
        int affectedRowCount = dao.delete(id);
        assertThat(affectedRowCount).isEqualTo(1);
    }

}
