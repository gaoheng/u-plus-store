package me.gaoheng.uplusstore.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class SKUImporting implements Serializable {

    private Long id;

    private String code;
    private String name;
    private String color;
    private String size;
    private BigDecimal price;
    private Integer quantity;

    private String batchNo;
    private String importStatus = "PENDING";

    private Date createTime = new Date();
    private Date updateTime = new Date();

}
