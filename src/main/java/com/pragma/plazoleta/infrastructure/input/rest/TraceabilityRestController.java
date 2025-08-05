package com.pragma.plazoleta.infrastructure.input.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.pragma.plazoleta.application.dto.request.TraceabilityRequestDto;
import com.pragma.plazoleta.application.dto.response.TraceabilityResponseDto;
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
    public ResponseEntity<TraceabilityResponseDto> saveTraceability(@Valid @RequestBody TraceabilityRequestDto traceabilityRequestDto) {
        TraceabilityResponseDto response = traceabilityHandler.saveTraceability(traceabilityRequestDto);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/restaurant/{restaurantId}")
    @PreAuthorize("hasAnyRole('OWNER')")
    @Operation(summary = "Get all traceability records", description = "Retrieves all traceability records")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Traceability records retrieved successfully")
    })
    public ResponseEntity<List<TraceabilityResponseDto>> getTraceabilityByRestaurantId(@PathVariable String restaurantId) {
        List<TraceabilityResponseDto> response = traceabilityHandler.getTraceabilityByRestaurantId(restaurantId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/order/{orderId}")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @Operation(summary = "Get traceability by order ID", description = "Retrieves traceability records for a specific order")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Traceability records retrieved successfully")
    })
    public ResponseEntity<List<TraceabilityResponseDto>> getTraceabilityByOrderId(@PathVariable String orderId) {
        List<TraceabilityResponseDto> response = traceabilityHandler.getTraceabilityByOrderId(orderId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/client")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @Operation(summary = "Get traceability by client ID", description = "Retrieves traceability records for a specific client")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Traceability records retrieved successfully")
    })
    public ResponseEntity<List<TraceabilityResponseDto>> getTraceabilityByClientId() {
        List<TraceabilityResponseDto> response = traceabilityHandler.getTraceabilityByClientId();
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasAnyRole('OWNER')")
    @Operation(summary = "Get traceability by employee ID", description = "Retrieves traceability records for a specific employee")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Traceability records retrieved successfully")
    })
    public ResponseEntity<List<TraceabilityResponseDto>> getTraceabilityByEmployeeId(@PathVariable String employeeId) {
        List<TraceabilityResponseDto> response = traceabilityHandler.getTraceabilityByEmployeeId(employeeId);
        return ResponseEntity.ok(response);
    }
} 