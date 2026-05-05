package dev.oudom.bank.loan.controller;

import dev.oudom.bank.loan.dto.ApiResponse;
import dev.oudom.bank.loan.dto.CreateLoanRequest;
import dev.oudom.bank.loan.dto.LoanResponse;
import dev.oudom.bank.loan.dto.RepaymentRequest;
import dev.oudom.bank.loan.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    public ResponseEntity<ApiResponse<LoanResponse>> apply(@Valid @RequestBody CreateLoanRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Loan application submitted", loanService.apply(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LoanResponse>> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok("Loan retrieved", loanService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<LoanResponse>>> findByCustomerId(@RequestParam UUID customerId) {
        return ResponseEntity.ok(ApiResponse.ok("Loans retrieved", loanService.findByCustomerId(customerId)));
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<ApiResponse<LoanResponse>> approve(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok("Loan approved", loanService.approve(id)));
    }

    @PatchMapping("/{id}/repay")
    public ResponseEntity<ApiResponse<LoanResponse>> repay(
            @PathVariable UUID id,
            @Valid @RequestBody RepaymentRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Repayment recorded", loanService.repay(id, request.amount())));
    }

    @PatchMapping("/{id}/close")
    public ResponseEntity<ApiResponse<LoanResponse>> close(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok("Loan closed", loanService.close(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable UUID id) {
        loanService.cancel(id);
        return ResponseEntity.noContent().build();
    }
}
