package me.gaoheng.uplusstore.dao;

import me.gaoheng.uplusstore.model.SKUImporting;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SKUImportingDao {

    @Insert("<script>" +
            "insert into skus_importing(batchNo, code, name, color, size, price, quantity, createTime) values " +
            "<foreach collection='list' item ='i' separator=','>" +
            "(#{i.batchNo}, #{i.code}, #{i.name}, #{i.color}, #{i.size}, #{i.price}, #{i.quantity}, #{i.createTime})" +
            "</foreach>" +
            "</script>")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(@Param("list") List<SKUImporting> list);

}
