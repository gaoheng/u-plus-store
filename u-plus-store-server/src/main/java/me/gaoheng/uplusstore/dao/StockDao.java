package me.gaoheng.uplusstore.dao;

import me.gaoheng.uplusstore.model.StockRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface StockDao {

    @Insert("insert into stock_record(" +
            "   skuId, type, delta, reason, createTime" +
            ") values (" +
            "   #{skuId}, #{type}, #{delta}, #{reason}, #{createTime}" +
            ")")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    int insert(StockRecord record);
}
