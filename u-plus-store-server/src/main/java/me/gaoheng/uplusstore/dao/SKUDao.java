package me.gaoheng.uplusstore.dao;

import me.gaoheng.uplusstore.model.SKU;
import org.apache.ibatis.annotations.*;

@Mapper
public interface SKUDao {

    @Select("select * from skus where id = #{id}")
    SKU get(@Param("id") Long id);

    @Select("select * from skus where code = #{code}")
    SKU getByCode(@Param("code") String code);

    @Update("insert into skus(" +
            "   name, code, color, size, mainImg, tagImg, createTime" +
            ") values (" +
            "   #{name}, #{code}, #{color}, #{size}, #{mainImg}, #{tagImg}, NOW())")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Long.class)
    int insert(SKU sku);

    @Update("update skus set " +
            "   name = #{name}, code = #{code}, color = #{color}, size = #{size}, mainImg = #{mainImg}, tagImg = #{tagImg}" +
            "where" +
            "   id = #{id}")
    int update(SKU sku);

    @Delete("delete from skus where id = #{id}")
    int delete(@Param("id") Long id);

}
