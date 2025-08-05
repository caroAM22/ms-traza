package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.domain.model.TraceabilityModel;

import java.util.List;
import java.util.UUID;

public interface ITraceabilityServicePort {
    TraceabilityModel saveTraceability(TraceabilityModel traceabilityModel);
    List<TraceabilityModel> getTraceabilityByRestaurantId(UUID restaurantId);
    List<TraceabilityModel> getTraceabilityByOrderId(UUID orderId);
    List<TraceabilityModel> getTraceabilityByClientId();
    List<TraceabilityModel> getTraceabilityByEmployeeId(UUID employeeId);
} 