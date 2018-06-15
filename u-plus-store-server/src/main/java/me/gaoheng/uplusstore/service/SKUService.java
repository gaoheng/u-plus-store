package me.gaoheng.uplusstore.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.gaoheng.uplusstore.dao.SKUDao;
import me.gaoheng.uplusstore.model.SKU;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class SKUService {

    @Autowired
    private SKUDao dao;

    @Autowired
    private StockService stockService;

    public SKU get(Long id) {
        SKU sku = dao.get(id);
        log.debug("Load sku, id: {}, sku: {}.", id, sku);
        return sku;
    }

    public SKU getByCode(String code) {
        SKU sku = dao.getByCode(code);
        log.debug("Load sku, code: {}, sku: {}.", code, sku);
        return sku;
    }

    public SKU save(SKU sku) {
        if (sku.getId() == null) {
            String code = sku.getCode();
            String codeSource = "ORIGINAL";
            if (StringUtils.isBlank(code)) {
                code = generateCode();
                codeSource = "GENERATED";
                sku.setCode(code);
                log.debug("Generate sku code: {} for {}.", code, sku);
            }
            sku.setCodeSource(codeSource);

            dao.insert(sku);
            log.debug("Insert sku, id: {}, sku: {}.", sku.getId(), sku);

            if (sku.getStock() > 0) {
                stockService.stockIn(sku.getId(), sku.getStock(), "上新");
            }
        } else {
            dao.update(sku);
            log.debug("Update sku, id: {}, sku: {}.", sku.getId(), sku);
        }

        return sku;
    }

    @PutMapping(path = "/{id}/stock")
    public void shangeStock(@PathVariable("id") Long id, @RequestBody Changement changement) {
        if(changement.getDelta() > 0) {
            stockService.stockIn(id, changement.getDelta(), changement.getReason());
        } else {
            stockService.stockOut(id, Math.abs(changement.getDelta()), changement.getReason());
        }
    }

    @Data
    public static final class Changement implements Serializable {
        Integer delta;
        String reason;
    }

    private String generateCode() {
        return DateFormatUtils.format(new Date(), "yyyyMMdd") + RandomStringUtils.randomNumeric(5);
    }

    public int count() {
        int count = dao.count();
        log.debug("Count sku, total: {}.", count);
        return count;
    }

    public List<SKU> list(Integer offset, Integer limit) {
        List<SKU> list = null;
        list = dao.list(offset, limit);
        if (list == null) {
            list = new ArrayList<>();
            log.debug("List sku, null list returned, offset: {}, limit: {}.", offset, limit);
        }
        log.debug("List sku, offset: {}, limit: {}, list: {}.", offset, limit, list);
        return list;
    }
}
