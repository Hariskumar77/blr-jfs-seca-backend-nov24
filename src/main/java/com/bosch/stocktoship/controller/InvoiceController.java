package com.bosch.stocktoship.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bosch.stocktoship.entity.Invoice;
import com.bosch.stocktoship.service.InvoiceService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class InvoiceController {

    @Autowired
    private InvoiceService service;

    @PostMapping(value = "/invoice")
    public Invoice createInvoice(@RequestBody Invoice invoice) {
        return service.createInvoice(invoice);
    }

    @PutMapping(value = "/invoice")
    public Invoice updateInvoice(@RequestBody Invoice invoice) {
        return service.updateInvoice(invoice);
    }

    @GetMapping(value = "/invoice/{id}")
    public Invoice getInvoice(@PathVariable long id) {
        return service.getInvoiceById(id);
    }

    @GetMapping(value = "/invoiceAll")
    public Iterable<Invoice> getAllInvoices() {
        return service.getAllInvoices();
    }

    @DeleteMapping(value = "/invoice/{id}")
    public void deleteInvoice(@PathVariable long id) {
        service.deleteInvoiceById(id);
    }

    @GetMapping(value = "/invoice/supplier/{poNumber}")
    public Invoice getInvoiceWithSupplier(@PathVariable long poNumber) {
        return service.getInvoiceWithSupplierByPoNumber(poNumber);
    }
}
