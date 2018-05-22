package me.gaoheng.uplusstore.dao;

import me.gaoheng.uplusstore.model.SKUImporting;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SKUImportingDao {

    @Insert("<script>" +
            "insert into sku_importing(batchNo, code, name, color, size, price, quantity, importStatus, createTime) values " +
            "<foreach collection='list' item ='i' separator=','>" +
            "(#{i.batchNo}, #{i.code}, #{i.name}, #{i.color}, #{i.size}, #{i.price}, #{i.quantity}, #{i.importStatus}, #{i.createTime})" +
            "</foreach>" +
            "</script>")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    int insert(@Param("list") List<SKUImporting> list);

    @Select("select * from sku_importing where batchNo = #{batchNo}")
    List<SKUImporting> listByBatchNo(@Param("batchNo") String batchNo);

    @Update("update sku_importing set importStatus = #{importStatus} where id = #{id}")
    int updateImportStatus(@Param("id") Long id, @Param("importStatus") String importStatus);
}
