package me.gaoheng.uplusstore.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderItem implements Serializable {
    private static final long serialVersionUID = 6365497996558598849L;

    private Long id;

    private Long orderId;

    private Long skuId;

    private BigDecimal price;

    private Integer quantity;

    private Date createTime;

    private Date updateTime;
}
