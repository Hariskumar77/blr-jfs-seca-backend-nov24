package com.bosch.stocktoship.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bosch.stocktoship.entity.Supplier;
import com.bosch.stocktoship.repository.SupplierRepository;

@Service
public class SupplierService{

    @Autowired
    private SupplierRepository repository;

    public Supplier createSupplier(Supplier supplier) {
        return repository.save(supplier);
    }

    public Supplier updateSupplier(Supplier supplier) {
        return repository.save(supplier);
    }

    public Supplier getSupplierById(long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Supplier not found"));
    }

    public Iterable<Supplier> getAllSuppliers() {
        return repository.findAll();
    }

    public void deleteSupplierById(long id) {
        repository.deleteById(id);
    }
}
