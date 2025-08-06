package com.pragma.plazoleta.infrastructure.input.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.pragma.plazoleta.application.dto.request.TraceabilityRequest;
import com.pragma.plazoleta.application.dto.response.TraceabilityResponse;
import com.pragma.plazoleta.application.dto.response.TraceabilityGroupedResponse;
import com.pragma.plazoleta.application.dto.response.OrderSummaryResponse;
import com.pragma.plazoleta.application.dto.response.EmployeeAverageTimeResponse;
import com.pragma.plazoleta.application.handler.ITraceabilityHandler;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/traceability")
@RequiredArgsConstructor
@Tag(name = "Traceability", description = "Traceability management endpoints")
public class TraceabilityRestController {
    
    private final ITraceabilityHandler traceabilityHandler;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'CUSTOMER')")
    @Operation(summary = "Save traceability record", description = "Creates a new traceability record")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Traceability record created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<TraceabilityResponse> saveTraceability(@Valid @RequestBody TraceabilityRequest traceabilityRequestDto) {
        TraceabilityResponse response = traceabilityHandler.saveTraceability(traceabilityRequestDto);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/restaurant/{restaurantId}")
    @PreAuthorize("hasAnyRole('OWNER')")
    @Operation(summary = "Get order summaries by restaurant", description = "Retrieves order summaries with start/end times for completed orders")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order summaries retrieved successfully")
    })
    public ResponseEntity<List<OrderSummaryResponse>> getOrdersTraceabilityByRestaurantId(@PathVariable String restaurantId) {
        List<OrderSummaryResponse> response = traceabilityHandler.getOrdersTraceabilityByRestaurantId(restaurantId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/order/{orderId}")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @Operation(summary = "Get traceability by order ID", description = "Retrieves traceability records for a specific order")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Traceability records retrieved successfully")
    })
    public ResponseEntity<List<TraceabilityResponse>> getTraceabilityByOrderId(@PathVariable String orderId) {
        List<TraceabilityResponse> response = traceabilityHandler.getTraceabilityByOrderId(orderId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/client/{clientId}")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @Operation(summary = "Get traceability by client ID", description = "Retrieves traceability records grouped by order ID for the authenticated client")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Traceability records retrieved successfully")
    })
    public ResponseEntity<List<TraceabilityGroupedResponse>> getTraceabilityByClientId(@PathVariable String clientId) {
        List<TraceabilityGroupedResponse> response = traceabilityHandler.getTraceabilityByClientId(clientId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/employee/{restaurantId}")
    @PreAuthorize("hasAnyRole('OWNER')")
    @Operation(summary = "Get employee ranking by restaurant", description = "Retrieves employee performance ranking with average order times")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Employee ranking retrieved successfully")
    })
    public ResponseEntity<List<EmployeeAverageTimeResponse>> getEmployeeRankingByRestaurantId(@PathVariable String restaurantId) {
        List<EmployeeAverageTimeResponse> response = traceabilityHandler.getEmployeeRankingByRestaurantId(restaurantId);
        return ResponseEntity.ok(response);
    } 
} 