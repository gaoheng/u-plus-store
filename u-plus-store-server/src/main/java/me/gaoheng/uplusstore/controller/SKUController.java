package me.gaoheng.uplusstore.controller;

import me.gaoheng.uplusstore.model.Page;
import me.gaoheng.uplusstore.model.SKU;
import me.gaoheng.uplusstore.service.SKUService;
import me.gaoheng.uplusstore.web.datatables.DTRequest;
import me.gaoheng.uplusstore.web.datatables.DTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/skus")
public class SKUController {

    @Autowired
    private SKUService skuService;

    @RequestMapping(method = RequestMethod.GET, params = {"code"})
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

}
