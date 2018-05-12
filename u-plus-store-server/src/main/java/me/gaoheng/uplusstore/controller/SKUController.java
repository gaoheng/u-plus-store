package me.gaoheng.uplusstore.controller;

import me.gaoheng.uplusstore.model.SKU;
import me.gaoheng.uplusstore.service.SKUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/skus")
public class SKUController {

    @Autowired
    private SKUService skuService;

    @RequestMapping(method = RequestMethod.GET, params = {"code"})
    public SKU sku(@RequestParam("code") String code) {

        return skuService.getByCode(code);
    }

}
