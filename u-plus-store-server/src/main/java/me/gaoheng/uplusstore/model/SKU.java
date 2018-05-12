package me.gaoheng.uplusstore.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SKU implements Serializable {

    private static final long serialVersionUID = 7930131093393018958L;

    private Long id;

    private String name;

    private String code;

    private BigDecimal price;

    private Integer stock;

    private String color;

    private String size;

    private String mainImg;

    private String tagImg;

}
