package me.gaoheng.uplusstore.dao;

import me.gaoheng.uplusstore.model.SKU;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SKUDao {

    @Select("select * from sku where id = #{id}")
    SKU get(@Param("id") Long id);

    @Select("select * from sku where code = #{code}")
    SKU getByCode(@Param("code") String code);

    @Update("insert into sku(" +
            "   name, code, price, color, size, mainImg, tagImg, source, createTime" +
            ") values (" +
            "   #{name}, #{code}, #{price}, #{color}, #{size}, #{mainImg}, #{tagImg}, #{source}, createTime)")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Long.class)
    int insert(SKU sku);

    @Update("update sku set " +
            "   name = #{name}, code = #{code}, color = #{color}, size = #{size}, mainImg = #{mainImg}, tagImg = #{tagImg}" +
            "where" +
            "   id = #{id}")
    int update(SKU sku);

    @Delete("delete from sku where id = #{id}")
    int delete(@Param("id") Long id);

    @Update("update sku set stock = stock + #{delta} where id = #{id}")
    int changeStock(@Param("id") Long id, @Param("delta") int delta);

    @Select("select count(*) from sku")
    int count();

    @Select("select * from sku order by id desc limit #{offset}, #{limit}")
    List<SKU> list(@Param("offset") int offset, @Param("limit") int limit);
}
