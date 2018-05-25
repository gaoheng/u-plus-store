package me.gaoheng.uplusstore.web.datatables;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DTResponse implements Serializable {

    private int draw;

    private int recordsTotal;

    private int recordsFiltered;

    private List<?> data;

    private String error;

}
