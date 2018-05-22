package me.gaoheng.uplusstore.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class SKU implements Serializable {

    private static final long serialVersionUID = 7930131093393018958L;

    private Long id;

    private String name = "";

    private String code = "";

    private BigDecimal price = new BigDecimal(0);

    private Integer stock = 0;

    private String color = "";

    private String size = "";

    private String mainImg = "";

    private String tagImg = "";

    private String source = "";

    private Date createTime = new Date();

    private Date updateTime = new Date();

}
