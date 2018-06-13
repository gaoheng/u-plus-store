package me.gaoheng.uplusstore.controller;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.gaoheng.uplusstore.dao.SKUImportingDao;
import me.gaoheng.uplusstore.model.SKU;
import me.gaoheng.uplusstore.model.SKUImporting;
import me.gaoheng.uplusstore.service.SKUService;
import me.gaoheng.uplusstore.service.StockService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sku-importings")
public class SkuImportingController {

    @Autowired
    private SKUImportingDao dao;

    @Autowired
    private SKUService skuService;

    @Autowired
    private StockService stockService;

    @RequestMapping(path = "/batches/{batchNo}", method = RequestMethod.PUT)
    public SkuImportingResult batchImport(@PathVariable("batchNo") String batchNo) {
        SkuImportingResult result = new SkuImportingResult();
        result.setBatchNo(batchNo);
        log.debug("Start batch importing, batchNo: {}.", batchNo);
        List<SKUImporting> list = dao.listByBatchNo(batchNo);
        result.setList(list);
        log.debug("Load {} items to process, batchNo: {}.", list.size(), batchNo);
        list.forEach(item -> {
            log.debug("Start importing item, id: {}.", item.getId());
            try {

                String importStatus = item.getImportStatus();
                switch (importStatus) {
                    case "PENDING":
                        doImportItem(item);
                        break;
                    default:
                        log.debug("Skip import non-pending item, id: {}, status: {}.", item.getId(), importStatus);
                }

                log.debug("Finished importing item, id: {}.", item.getId());
            } catch (Throwable t) {
                log.error("Error when importing item, id: " + item.getId() + ".", t);
            }
        });
        return result;
    }

    private void doImportItem(SKUImporting item) {
        String code = item.getCode();
        String codeSource = "ORIGINAL";
        if (StringUtils.isBlank(code)) {
            code = generateCode();
            codeSource = "GENERATED";
            item.setCode(code);
            log.debug("Generate sku code: {} for importing: {}.", code, item);
        }
        item.setCodeSource(codeSource);
        SKU sku = skuService.getByCode(code);
        if (sku == null) {
            log.debug("SKU[{}] not exists, creating...", code);
            sku = createSKU(item, "批量入库");
        }

        stockService.stockIn(sku.getId(), item.getQuantity(), "批量导入");

        item.setImportStatus("DONE");
        dao.updateImportStatus(item.getId(), item.getImportStatus());
    }

    private SKU createSKU(SKUImporting item, String source) {
        SKU sku = new SKU();
        sku.setCode(item.getCode());
        sku.setCodeSource(item.getCodeSource());
        sku.setName(item.getName());
        sku.setColor(item.getColor());
        sku.setSize(item.getSize());
        sku.setPrice(item.getPrice());
        sku.setSource(source);

        sku = skuService.save(sku);
        log.debug("SKU created: {}.", sku);
        return sku;

    }

    @RequestMapping(method = RequestMethod.POST)
    public SkuImportingResult upload(@RequestParam("file") MultipartFile multipartFile) {

        Date now = new Date();
        String batchNo = DateFormatUtils.format(now, "yyyyMMddHHmmssSSS_") + RandomStringUtils.randomNumeric(6);
        log.debug("Batch no generated");
        SkuImportingResult result = new SkuImportingResult();
        result.setBatchNo(batchNo);
        log.debug("Multipart file: {}.", multipartFile);
        List<SKUImporting> importings = Lists.newArrayList();
        try {
            Workbook wb = WorkbookFactory.create(multipartFile.getInputStream());
            Sheet sheet = wb.getSheetAt(0);
            String sheetName = sheet.getSheetName();
            log.debug("Start processing sheet[].", sheet.getSheetName());
            int skipRows = 1;
            int skipped = 0;
            for (Row row : sheet) {
                log.debug("Start processing row[{}] of sheet[].", row.getRowNum(), sheetName);

                if (skipped < skipRows) {
                    skipped++;
                    log.debug("Skip row[{}].", skipped);
                    continue;
                }

                Cell codeCell = row.getCell(0);
                String code = getStringCellValue(codeCell, "");
                Cell nameCell = row.getCell(1);
                String name = getStringCellValue(nameCell, "");

                if(StringUtils.isBlank(code) && StringUtils.isBlank(name)) {
                    log.warn("Blank row detected, row num: {}.", row.getRowNum());
                    break;
                }

                Cell colorCell = row.getCell(2);
                String color = getStringCellValue(colorCell, "");
                Cell sizeCell = row.getCell(3);
                String size = getStringCellValue(sizeCell, "");
                Cell priceCell = row.getCell(4);
                Cell quantityCell = row.getCell(5);

                SKUImporting s = new SKUImporting();
                importings.add(s);

                s.setImportStatus("PENDING");
                s.setBatchNo(batchNo);
                s.setCode(code);
                s.setName(name);
                s.setColor(color);
                s.setSize(size);
                s.setPrice(new BigDecimal(getStringCellValue(priceCell)));
                s.setQuantity(new BigDecimal(getStringCellValue(quantityCell)).intValue());
                s.setCreateTime(now);
                s.setUpdateTime(now);
            }

            if (importings.size() > 0) {
                doImport(importings);
                result.setList(importings);
            }

        } catch (IOException e) {
            log.error("Error when import from Excel file.", e);
        } catch (InvalidFormatException e) {
            log.error("Error when import from Excel file.", e);
        }

        return result;
    }

    private String getStringCellValue(Cell cell, String defaultValue) {
        if (cell == null) {
            return defaultValue;
        }
        return getStringCellValue(cell);
    }

    @Data
    public static class SkuImportingResult implements Serializable {
        private String batchNo;
        private List<SKUImporting> list;
    }

    private String getStringCellValue(Cell cell) {
        String val = null;

        CellType type = cell.getCellTypeEnum();
        switch (type) {
            case BLANK:
                return "";
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
        }

        return val;
    }

    private String generateCode() {
        return DateFormatUtils.format(new Date(), "yyyyMMdd") + RandomStringUtils.randomNumeric(5);
    }

    private void doImport(List<SKUImporting> importings) {
        int affectedRowCount = dao.insert(importings);
        log.debug("Insert [{}] rows.", affectedRowCount);
    }

}
