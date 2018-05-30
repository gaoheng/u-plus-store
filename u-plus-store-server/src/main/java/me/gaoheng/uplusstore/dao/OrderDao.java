package me.gaoheng.uplusstore.dao;

import me.gaoheng.uplusstore.model.Order;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderDao {

    @Select("select * from `order` where id = #{id}")
    Order get(@Param("id") Long id);

    @Update("insert into `order`(total, discount, paid, createTime) values (#{total}, #{discount}, #{paid}, #{createTime})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", keyColumn = "id", resultType = Long.class, before = false)
    int insert(Order order);

    @Update("update `order` set total = #{total}, discount = #{discount}, paid = #{paid} where id = #{id}")
    int update(Order order);

    @Delete("delete from `order` where id = #{id}")
    int delete(@Param("id") Long id);

}
