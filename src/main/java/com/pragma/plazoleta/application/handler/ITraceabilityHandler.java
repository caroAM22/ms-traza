package com.pragma.plazoleta.application.handler;

import java.util.List;

import com.pragma.plazoleta.application.dto.request.TraceabilityRequest;
import com.pragma.plazoleta.application.dto.response.TraceabilityResponse;
import com.pragma.plazoleta.application.dto.response.TraceabilityGroupedResponse;
import com.pragma.plazoleta.application.dto.response.OrderSummaryResponse;
import com.pragma.plazoleta.application.dto.response.EmployeeAverageTimeResponse;

public interface ITraceabilityHandler {
    TraceabilityResponse saveTraceability(TraceabilityRequest traceabilityRequestDto);
    List<OrderSummaryResponse> getOrdersTraceabilityByRestaurantId(String restaurantId);
    List<TraceabilityResponse> getTraceabilityByOrderId(String orderId);
    List<TraceabilityGroupedResponse> getTraceabilityByClientId(String clientId);
    List<EmployeeAverageTimeResponse> getEmployeeRankingByRestaurantId(String restaurantId);
} 