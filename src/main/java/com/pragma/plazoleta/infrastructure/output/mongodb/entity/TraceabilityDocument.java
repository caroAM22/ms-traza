package com.pragma.plazoleta.infrastructure.output.mongodb.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "traceability")
public class TraceabilityDocument {
    @Id
    private String id;
    private String orderId;
    private String clientId;
    private String clientEmail;
    private LocalDateTime date;
    private String previousState;
    private String newState;
    private String employeeId;
    private String employeeEmail;
    private String restaurantId;
} 