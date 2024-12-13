package com.bosch.stocktoship;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import com.bosch.stocktoship.entity.Invoice;
import com.bosch.stocktoship.service.InvoiceService;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringBootTest
@AutoConfigureMockMvc
public class InvoiceControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceService invoiceService;

    @Test
    public void testGetInvoiceById_Success() throws Exception {
        // Given
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(1L);
        invoice.setInvoiceDate(new Date(1704067200000L));
        invoice.setAmount(1500.75);
        invoice.setDueDate(new Date(1704067200000L));
        invoice.setPoNumber(3001L);
        invoice.setSupplierId(101);
        invoice.setApprovedByStoreManager(true);
        invoice.setApprovedByAccount(true);
        invoice.setApprovedByAccountManager(true);

        // When
        when(invoiceService.getInvoiceById(3001L)).thenReturn(invoice);

        // Then
        mockMvc.perform(get("/api/invoice/3001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.invoiceNumber").value(1))
                .andExpect(jsonPath("$.amount").value(1500.75))
                .andExpect(jsonPath("$.poNumber").value(3001))
                .andExpect(jsonPath("$.supplierId").value(101))
                .andExpect(jsonPath("$.approvedByStoreManager").value(true))
                .andExpect(jsonPath("$.approvedByAccount").value(true))
                .andExpect(jsonPath("$.approvedByAccountManager").value(true));
    }

    @Test
    public void testGetInvoiceById_NotFound() throws Exception {
        // When
        when(invoiceService.getInvoiceById(anyLong())).thenThrow(new RuntimeException("Invoice not found"));

        // Then
        mockMvc.perform(get("/invoice/1"))
                .andExpect(status().isNotFound());
    }
}
