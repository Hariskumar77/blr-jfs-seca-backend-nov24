package com.bosch.stocktoship.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bosch.stocktoship.entity.Supplier;
import com.bosch.stocktoship.service.SupplierService;
@RestController
@RequestMapping("/api")
public class SupplierController {

    @Autowired
    private SupplierService service;

    @PostMapping(value = "/supplier")
    public Supplier createSupplier(@RequestBody Supplier supplier) {
        return service.createSupplier(supplier);
    }

    @PutMapping(value = "/supplier")
    public Supplier updateSupplier(@RequestBody Supplier supplier) {
        return service.updateSupplier(supplier);
    }

    @GetMapping(value = "/supplier/{id}")
    public Supplier getSupplier(@PathVariable long id) {
        return service.getSupplierById(id);
    }

    @GetMapping(value = "/supplierAll")
    public Iterable<Supplier> getAllSuppliers() {
        return service.getAllSuppliers();
    }

    @DeleteMapping(value = "/supplier/{id}")
    public void deleteSupplier(@PathVariable long id) {
        service.deleteSupplierById(id);
    }
}
