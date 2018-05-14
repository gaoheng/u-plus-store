package me.gaoheng.uplusstore.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import me.gaoheng.uplusstore.dao.SKUImportingDao;
import me.gaoheng.uplusstore.model.SKUImporting;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/files")
public class FileUploadController {

    private SKUImportingDao dao;

    @RequestMapping(method = RequestMethod.POST)
    public List<SKUImporting> upload(@RequestParam("file") String fileName, MultipartFile multipartFile) {

        List<SKUImporting> importings = Lists.newArrayList();
        try {
            Date now = new Date();
            String batchNo = DateFormatUtils.format(now, "yyyyMMddHHmmssSSS_") + RandomStringUtils.randomNumeric(6);
            Workbook wb = WorkbookFactory.create(multipartFile.getInputStream());
            Sheet sheet = wb.getSheetAt(0);
            String sheetName = sheet.getSheetName();
            log.debug("Start processing sheet[].", sheet.getSheetName());
            for (Row row : sheet) {
                log.debug("Start processing row[{}] of sheet[].", row.getRowNum(), sheetName);

                Cell codeCell = row.getCell(0);
                if("EOF".equals(codeCell.getStringCellValue())) {
                    break;
                }
                Cell nameCell = row.getCell(1);
                Cell colorCell = row.getCell(2);
                Cell sizeCell = row.getCell(3);
                Cell priceCell = row.getCell(4);
                Cell quantityCell = row.getCell(5);

                SKUImporting s = new SKUImporting();
                importings.add(s);

                s.setImportStatus("PENDING");
                s.setBatchNo(batchNo);
                String code = codeCell.getStringCellValue();
                if(StringUtils.isBlank(code)) {
                    code = generateCode();
                }
                s.setCode(code);
                s.setName(nameCell.getStringCellValue());
                s.setColor(colorCell.getStringCellValue());
                s.setSize(sizeCell.getStringCellValue());
                s.setPrice(new BigDecimal(priceCell.getStringCellValue()));
                s.setQuantity(Integer.parseInt(quantityCell.getStringCellValue()));
                s.setCreateTime(now);
                s.setUpdateTime(now);
            }

            if(importings.size() > 0) {
                doImport(importings);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        return importings;
    }

    private String generateCode() {
        return null;
    }

    private void doImport(List<SKUImporting> importings) {
        int affectedRowCount = dao.insert(importings);
        log.debug("Insert [{}] rows.", affectedRowCount);
    }

    public static class FileMetadata implements Serializable {

    }

}
