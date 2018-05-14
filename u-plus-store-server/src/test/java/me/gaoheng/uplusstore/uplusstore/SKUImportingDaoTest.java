package me.gaoheng.uplusstore.uplusstore;

import me.gaoheng.uplusstore.dao.SKUImportingDao;
import me.gaoheng.uplusstore.model.SKUImporting;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SKUImportingDaoTest {

    @Autowired
    private SKUImportingDao dao;

    @Test
    public void testBatchInsert() {
        List<SKUImporting> list = Lists.newArrayList();

        for(int i = 1; i <= 5; i++) {
            SKUImporting sku = new SKUImporting();
            sku.setBatchNo("Batch:" + i);
            sku.setCode("Code:" + i);
            sku.setName("Name:" + i);
            sku.setColor("Color:" + i);
            sku.setSize("Size:" + i);
            sku.setPrice(new BigDecimal(i));
            sku.setQuantity(i);

            list.add(sku);
        }

        int affectedRowCount = dao.insert(list);
        assertThat(affectedRowCount).isEqualTo(5);
        list.forEach(sku -> {
            assertThat(sku.getId()).isNotNull();
        });
    }

}
