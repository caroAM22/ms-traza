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
public class EmployeeAverageTimeModel {
    private UUID employeeId;
    private Duration averageTime;
    
    public String getFormattedAverageTime() {
        if (averageTime == null) {
            return null;
        }
        
        long totalSeconds = averageTime.getSeconds();
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        
        if (minutes > 0) {
            return String.format("%dm %ds", minutes, seconds);
        } else {
            return String.format("%ds", seconds);
        }
    }
} 