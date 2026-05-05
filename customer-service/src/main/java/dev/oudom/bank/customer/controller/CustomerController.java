package dev.oudom.bank.customer.controller;

import dev.oudom.bank.customer.dto.ApiResponse;
import dev.oudom.bank.customer.dto.CreateCustomerRequest;
import dev.oudom.bank.customer.dto.CustomerResponse;
import dev.oudom.bank.customer.dto.UpdateCustomerRequest;
import dev.oudom.bank.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<ApiResponse<CustomerResponse>> create(@Valid @RequestBody CreateCustomerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Customer created successfully", customerService.create(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok("Customer retrieved", customerService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<CustomerResponse>> findByEmail(@RequestParam String email) {
        return ResponseEntity.ok(ApiResponse.ok("Customer retrieved", customerService.findByEmail(email)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateCustomerRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Customer updated", customerService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
