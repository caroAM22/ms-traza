package com.pragma.plazoleta.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeePerformanceModel {
    private UUID employeeId;
    private String employeeEmail;
    private Duration averageOrderTime;
    private int totalOrders;
} 