package dev.oudom.bank.loan.service;

import dev.oudom.bank.loan.dto.CreateLoanRequest;
import dev.oudom.bank.loan.dto.LoanResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface LoanService {

    LoanResponse apply(CreateLoanRequest request);

    LoanResponse findById(UUID id);

    List<LoanResponse> findByCustomerId(UUID customerId);

    LoanResponse approve(UUID id);

    LoanResponse repay(UUID id, BigDecimal amount);

    LoanResponse close(UUID id);

    void cancel(UUID id);
}
