package dev.oudom.account.controller;

import dev.oudom.account.dto.ApiResponse;
import dev.oudom.account.dto.CreateCustomerRequest;
import dev.oudom.account.dto.CreateCustomerResponse;
import dev.oudom.account.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateCustomerResponse>> createCustomer(@Valid @RequestBody CreateCustomerRequest createCustomerRequest) {
        CreateCustomerResponse createCustomerResponse = customerService.createCustomer(createCustomerRequest);
        ApiResponse<CreateCustomerResponse> response = new ApiResponse<>(createCustomerResponse);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

}
