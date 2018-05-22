package me.gaoheng.uplusstore.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class StockRecord implements Serializable {

    private Long id;

    private Long skuId;

    private Type type;

    private Integer delta = 0;

    private String reason = "";

    private Date createTime = new Date();

    private Date updateTime = new Date();

    public enum Type {
        IN, OUT
    }

}
