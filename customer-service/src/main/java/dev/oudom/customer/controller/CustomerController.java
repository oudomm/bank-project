package dev.oudom.customer.controller;

import dev.oudom.customer.dto.ApiResponse;
import dev.oudom.customer.dto.CreateCustomerRequest;
import dev.oudom.customer.dto.CreateCustomerResponse;
import dev.oudom.customer.dto.CustomerResponse;
import dev.oudom.customer.dto.ErrorResponseDto;
import dev.oudom.customer.dto.UpdateCustomerRequest;
import dev.oudom.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Validated
@Tag(
        name = "Customer APIs",
        description = "Create, fetch, update, and delete digital banking customers"
)
public class CustomerController {

    private final CustomerService customerService;

    @Operation(summary = "Create customer", description = "Creates a new customer profile.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Customer created"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping
    public ResponseEntity<ApiResponse<CreateCustomerResponse>> createCustomer(
            @Valid @RequestBody CreateCustomerRequest createCustomerRequest
    ) {
        CreateCustomerResponse createCustomerResponse = customerService.createCustomer(createCustomerRequest);
        ApiResponse<CreateCustomerResponse> response = new ApiResponse<>(createCustomerResponse);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Operation(summary = "Fetch customer", description = "Returns customer details for the provided customer id.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Customer returned"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Customer not found", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/{customerId}")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomer(
            @Parameter(description = "Customer id")
            @PathVariable UUID customerId
    ) {
        CustomerResponse customerResponse = customerService.getCustomer(customerId);
        ApiResponse<CustomerResponse> response = new ApiResponse<>(customerResponse);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Fetch customer by phone number", description = "Returns customer details for the provided 10-digit phone number.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Customer returned"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid phone number", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Customer not found", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomerByPhoneNumber(
            @Parameter(description = "10-digit phone number", example = "9876543210")
            @RequestParam
            @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be exactly 10 digits")
            String phoneNumber
    ) {
        CustomerResponse customerResponse = customerService.getCustomerByPhoneNumber(phoneNumber);
        ApiResponse<CustomerResponse> response = new ApiResponse<>(customerResponse);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update customer", description = "Updates customer profile details.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Customer updated"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Customer not found", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/{customerId}")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateCustomer(
            @Parameter(description = "Customer id")
            @PathVariable UUID customerId,
            @Valid @RequestBody UpdateCustomerRequest updateCustomerRequest
    ) {
        CustomerResponse customerResponse = customerService.updateCustomer(customerId, updateCustomerRequest);
        ApiResponse<CustomerResponse> response = new ApiResponse<>(customerResponse);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete customer", description = "Deletes a customer profile by id.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Customer deleted"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Customer not found", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(
            @Parameter(description = "Customer id")
            @PathVariable UUID customerId
    ) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }
}
