package me.gaoheng.uplusstore.service;

import lombok.extern.slf4j.Slf4j;
import me.gaoheng.uplusstore.dao.SKUDao;
import me.gaoheng.uplusstore.dao.StockDao;
import me.gaoheng.uplusstore.model.StockRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StockService {

    @Autowired
    private StockDao stockDao;

    @Autowired
    private SKUDao skuDao;

    public void stockIn(Long skuId, int increament, String reason) {

        StockRecord record = new StockRecord();
        record.setSkuId(skuId);
        record.setType(StockRecord.Type.IN);
        record.setDelta(increament);
        record.setReason(reason);

        int affectedRowCount = stockDao.insert(record);
        log.debug("Insert {} stock record(s): {}.", affectedRowCount);

        skuDao.changeStock(skuId, record.getDelta());
        log.debug("SKU[{}] stock changed, delta: {}, reason: {}.", skuId, record.getDelta(), reason);

    }

    public void stockOut(Long skuId, int decreament, String reason) {
        StockRecord record = new StockRecord();
        record.setSkuId(skuId);
        record.setType(StockRecord.Type.OUT);
        record.setDelta(decreament * -1);
        record.setReason(reason);

        int affectedRowCount = stockDao.insert(record);
        log.debug("Insert {} stock record(s): {}.", affectedRowCount);

        skuDao.changeStock(skuId, record.getDelta());
        log.debug("SKU[{}] stock changed, delta: {}, reason: {}.", skuId, record.getDelta(), reason);
    }

}
