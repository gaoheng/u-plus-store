package me.gaoheng.uplusstore.dao;

import me.gaoheng.uplusstore.model.Order;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderDao {

    @Select("select * from orders where id = #{id}")
    Order get(@Param("id") Long id);

    @Update("insert into orders(total, discounted, actuallyPaid, createTime) values (#{total}, #{discounted}, #{actuallyPaid}, NOW())")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", keyColumn = "id", resultType = Long.class, before = false)
    int insert(Order order);

    @Update("update orders set total = #{total}, discounted = #{discounted}, actuallyPaid = #{actuallyPaid} where id = #{id}")
    int update(Order order);

    @Delete("delete from orders where id = #{id}")
    int delete(@Param("id") Long id);

}
