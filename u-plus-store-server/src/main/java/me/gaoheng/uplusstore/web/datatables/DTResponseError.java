package me.gaoheng.uplusstore.web.datatables;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class DTResponseError extends DTResponse implements Serializable {

    @Getter
    @Setter
    private String error;

}
