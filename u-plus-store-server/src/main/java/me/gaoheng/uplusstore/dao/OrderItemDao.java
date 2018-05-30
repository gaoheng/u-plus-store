package me.gaoheng.uplusstore.dao;

import me.gaoheng.uplusstore.model.OrderItem;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderItemDao {

    @Select("select * from order_item where id = #{id}")
    OrderItem get(@Param("id") Long id);

    @Update("insert into order_item(" +
            "   orderId, skuId, skuName, skuCode, skuColor, skuSize, skuPrice, quantity, createTime" +
            ") values (" +
            "   #{orderId}, #{skuId}, #{skuName}, #{skuCode}, #{skuColor}, #{skuSize}, #{skuPrice}, #{quantity}, #{createTime})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", keyColumn = "id", resultType = Long.class, before = false)
    int insert(OrderItem order);

    @Delete("delete from order_item where id = #{id}")
    int delete(@Param("id") Long id);

}
