package me.gaoheng.uplusstore.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Order implements Serializable {
    private static final long serialVersionUID = -7201366103614827361L;

    private Long id;

    private BigDecimal total;

    private BigDecimal discount;

    private BigDecimal paid;

    private Date createTime = new Date();

    private Date updateTime = new Date();
}
