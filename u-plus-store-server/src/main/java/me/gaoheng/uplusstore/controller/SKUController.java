package me.gaoheng.uplusstore.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.gaoheng.uplusstore.model.Page;
import me.gaoheng.uplusstore.model.SKU;
import me.gaoheng.uplusstore.service.SKUService;
import me.gaoheng.uplusstore.service.StockService;
import me.gaoheng.uplusstore.web.datatables.DTRequest;
import me.gaoheng.uplusstore.web.datatables.DTResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/skus")
public class SKUController {

    @Autowired
    private SKUService skuService;

    @Autowired
    private StockService stockService;

    @GetMapping(path = "/{id}")
    public SKU sku(@PathVariable("id") Long id) {

        return skuService.get(id);
    }

    @GetMapping(path = "", params = {"code"})
    public SKU sku(@RequestParam("code") String code) {

        return skuService.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.GET)
    public DTResponse list(DTRequest dtRequest) {
        return dtRequest.handle(() -> {
            Page<SKU> page = new Page<>();
            int total = skuService.count();
            List<SKU> list = null;
            if (total > 0) {
                list = skuService.list(dtRequest.getStart(), dtRequest.getLength());
            }
            if (list == null) {
                list = new ArrayList<>();
            }

            page.setTotal(total);
            page.setList(list);
            return page;
        });

    }

    @PutMapping("/{id}")
    public SKU update(@PathVariable("id") Long id, @RequestBody Updating updating) {

        SKU sku = new SKU();
        sku.setId(id);
        sku.setName(updating.getName());
        sku.setColor(updating.getColor());
        sku.setSize(updating.getSize());
        sku.setPrice(updating.getPrice());

        sku = skuService.save(sku);

        return sku;
    }

    @Data
    public static final class Updating implements Serializable {
        private String name;
        private String color;
        private String size;
        private BigDecimal price;
    }

    @PostMapping
    public SKU create(@RequestBody Creating creating) {
        SKU sku = new SKU();
        sku.setCode(creating.getCode());
        sku.setName(creating.getName());
        sku.setColor(creating.getColor());
        sku.setSize(creating.getSize());
        sku.setPrice(creating.getPrice());
        sku.setStock(creating.getStock());
        sku = skuService.save(sku);

        return sku;
    }

    @Data
    public static final class Creating implements Serializable {
        private String code;
        private String name;
        private String color;
        private String size;
        private BigDecimal price;
        private Integer stock;
    }

    @PutMapping("/{id}/stock")
    public Changement stock(@PathVariable("id") Long id, @RequestBody Changement changement) {
        if (changement.getDelta() > 0) {
            stockService.stockIn(id, changement.getDelta(), changement.getReason());
        } else {
            stockService.stockOut(id, Math.abs(changement.getDelta()), changement.getReason());
        }
        return changement;
    }

    @Data
    public static final class Changement implements Serializable {
        private Integer delta;
        private String reason;
    }

}
