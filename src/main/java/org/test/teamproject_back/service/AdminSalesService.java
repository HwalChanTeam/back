package org.test.teamproject_back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.test.teamproject_back.dto.response.RespGraphDataDto;
import org.test.teamproject_back.dto.response.RespSalesDto;
import org.test.teamproject_back.entity.Payment;
import org.test.teamproject_back.entity.Product;
import org.test.teamproject_back.repository.AdminOrderMapper;
import org.test.teamproject_back.repository.PaymentsMapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AdminSalesService {

    @Autowired
    private PaymentsMapper paymentsMapper;

    public RespSalesDto getSalesList() {
        String paymentStatus = "결제완료";
        Long amount = paymentsMapper.getPaymentList(paymentStatus.trim())
                .stream()
                .mapToLong(Payment::getAmount)
                .sum();

        return RespSalesDto.builder()
                .paymentList(paymentsMapper.getPaymentList(paymentStatus.trim()))
                .amount(amount)
                .build();

    }

    public RespSalesDto getMonthSalesList(String date) {
        String paymentStatus = "결제완료";
        LocalDate formatDate = LocalDate.parse(date,  DateTimeFormatter.ISO_DATE);

        Long amount = paymentsMapper.getMonthPaymentList(formatDate, paymentStatus.trim())
                .stream()
                .mapToLong(Payment::getAmount)
                .sum();
        return RespSalesDto.builder()
                .paymentList(paymentsMapper.getMonthPaymentList(formatDate, paymentStatus.trim()))
                .amount(amount)
                .build();
    }

    public RespGraphDataDto getGraphData() {
        List<Product> productList = paymentsMapper.getGraphData();
        return RespGraphDataDto.builder()
                .products(productList)
                .build();
    }

}
