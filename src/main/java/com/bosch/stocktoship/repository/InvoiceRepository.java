package com.bosch.stocktoship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bosch.stocktoship.entity.Invoice;


public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query("SELECT i FROM Invoice i JOIN FETCH i.supplier WHERE i.poNumber = :poNumber")
    Invoice findInvoiceWithSupplierByPoNumber(@Param("poNumber") long poNumber);
}
