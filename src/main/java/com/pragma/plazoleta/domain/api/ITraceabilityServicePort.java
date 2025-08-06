package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.domain.model.TraceabilityModel;
import com.pragma.plazoleta.domain.model.OrderSummaryModel;
import com.pragma.plazoleta.domain.model.TraceabilityGroupedModel;
import com.pragma.plazoleta.domain.model.EmployeeAverageTimeModel;

import java.util.List;
import java.util.UUID;

public interface ITraceabilityServicePort {
    TraceabilityModel saveTraceability(TraceabilityModel traceabilityModel);
    List<OrderSummaryModel> getOrdersTraceabilityByRestaurantId(UUID restaurantId);
    List<EmployeeAverageTimeModel> getEmployeeTraceabilityByRestaurantId(UUID restaurantId);
    List<TraceabilityModel> getTraceabilityByOrderId(UUID orderId);
    List<TraceabilityGroupedModel> getTraceabilityByClientId(UUID clientId);
} 