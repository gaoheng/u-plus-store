package me.gaoheng.uplusstore.web.datatables;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.gaoheng.uplusstore.model.Page;

import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.function.Function;

@Slf4j
@Data
public class DTRequest implements Serializable {

    private int draw;

    private int start;

    private int length;

    public DTResponse handle(Callable<Page<?>> handler) {

        DTResponse dtResponse = null;
        Page<?> page = null;
        try {
            page = handler.call();
            dtResponse = new DTResponse();
            dtResponse.setDraw(this.getDraw());
            dtResponse.setRecordsTotal(page.getTotal());
            dtResponse.setRecordsFiltered(page.getTotal());
            dtResponse.setData(page.getList());
        } catch (Exception e) {
            log.error("Error when handle DTRequest.", e);
            dtResponse = new DTResponseError();
            dtResponse.setError("系统异常！");
        }
        return dtResponse;
    }

}
