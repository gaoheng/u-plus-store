package me.gaoheng.uplusstore.dao;

import me.gaoheng.uplusstore.model.OrderItem;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderItemDao {

    @Select("select * from order_items where id = #{id}")
    OrderItem get(@Param("id") Long id);

    @Update("insert into order_items(orderId, skuId, price, quantity, createTime) values (#{orderId}, #{skuId}, #{price}, #{quantity}, NOW())")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", keyColumn = "id", resultType = Long.class, before = false)
    int insert(OrderItem order);

    @Update("update order_items set orderId = #{orderId}, skuId = #{skuId}, price = #{price}, quantity = #{quantity} where id = #{id}")
    int update(OrderItem order);

    @Delete("delete from order_items where id = #{id}")
    int delete(@Param("id") Long id);

}
