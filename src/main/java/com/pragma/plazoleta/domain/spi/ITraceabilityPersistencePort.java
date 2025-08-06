package com.pragma.plazoleta.domain.spi;

import com.pragma.plazoleta.domain.model.TraceabilityModel;

import java.util.List;
import java.util.UUID;

public interface ITraceabilityPersistencePort {
    TraceabilityModel saveTraceability(TraceabilityModel traceabilityModel);
    List<TraceabilityModel> findByRestaurantId(UUID restaurantId);
    List<TraceabilityModel> findByOrderId(UUID orderId);
    List<TraceabilityModel> findByClientId(UUID clientId);
    List<TraceabilityModel> findByEmployeeId(UUID employeeId);
} 