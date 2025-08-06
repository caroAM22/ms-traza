package com.pragma.plazoleta.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.pragma.plazoleta.domain.utils.RegexPattern;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TraceabilityRequest {
    @NotNull(message = "Order ID is required")
    @Pattern(regexp = RegexPattern.UUID_PATTERN, 
             message = "Order ID must be a valid UUID format")
    private String orderId;
    
    @NotBlank(message = "Client ID is required")
    @Pattern(regexp = RegexPattern.UUID_PATTERN, 
             message = "Client ID must be a valid UUID format")
    private String clientId;
    
    @NotBlank(message = "Client email is required")
    @Email(message = "Client email must be valid")
    private String clientEmail;
    
    
    @Pattern(regexp = RegexPattern.ORDER_STATUS_PATTERN, 
             message = "Previous state must be one of: PENDING, IN_PROGRESS, READY, DELIVERED, CANCELLED")
    private String previousState;
    
    @NotBlank(message = "New state is required")
    @Pattern(regexp = RegexPattern.ORDER_STATUS_PATTERN, 
             message = "New state must be one of: PENDING, IN_PROGRESS, READY, DELIVERED, CANCELLED")
    private String newState;
    
    @Pattern(regexp = RegexPattern.UUID_PATTERN, 
             message = "Employee ID must be a valid UUID format or empty")
    private String employeeId;
    
    @Email(message = "Employee email must be valid")
    @Pattern(regexp = RegexPattern.EMAIL_PATTERN_REQUIRED, 
             message = "Employee email must be valid")
    private String employeeEmail;
    
    @NotNull(message = "Restaurant ID is required")
    @Pattern(regexp = RegexPattern.UUID_PATTERN, 
             message = "Restaurant ID must be a valid UUID format")
    private String restaurantId;
} 