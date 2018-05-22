package me.gaoheng.uplusstore.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Page<T> implements Serializable {

    public Integer total;

    public List<T> list;

}
