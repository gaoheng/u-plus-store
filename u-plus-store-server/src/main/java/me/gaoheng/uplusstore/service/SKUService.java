package me.gaoheng.uplusstore.service;

import lombok.extern.slf4j.Slf4j;
import me.gaoheng.uplusstore.dao.SKUDao;
import me.gaoheng.uplusstore.model.SKU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SKUService {

    @Autowired
    private SKUDao dao;

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
        if(sku.getId() == null) {
            dao.insert(sku);
            log.debug("Insert sku, id: {}, sku: {}.", sku.getId(), sku);
        } else {
            dao.update(sku);
            log.debug("Update sku, id: {}, sku: {}.", sku.getId(), sku);
        }

        return sku;
    }

}
