package com.bosch.stocktoship.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bosch.stocktoship.entity.Invoice;
import com.bosch.stocktoship.repository.InvoiceRepository;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository repository;

    public Invoice createInvoice(Invoice invoice) {
        return repository.save(invoice);
    }

    public Invoice updateInvoice(Invoice invoice) {
        return repository.save(invoice);
    }

    public Invoice getInvoiceById(long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Invoice not found"));
    }

    public Iterable<Invoice> getAllInvoices() {
        return repository.findAll();
    }

    public void deleteInvoiceById(long id) {
        repository.deleteById(id);
    }

    public Invoice getInvoiceWithSupplierByPoNumber(long poNumber) {
        return repository.findInvoiceWithSupplierByPoNumber(poNumber);
    }
}
