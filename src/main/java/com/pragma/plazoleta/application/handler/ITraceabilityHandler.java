package com.pragma.plazoleta.application.handler;

import java.util.List;

import com.pragma.plazoleta.application.dto.request.TraceabilityRequestDto;
import com.pragma.plazoleta.application.dto.response.TraceabilityResponseDto;

public interface ITraceabilityHandler {
    TraceabilityResponseDto saveTraceability(TraceabilityRequestDto traceabilityRequestDto);
    List<TraceabilityResponseDto> getTraceabilityByRestaurantId(String restaurantId);
    List<TraceabilityResponseDto> getTraceabilityByOrderId(String orderId);
    List<TraceabilityResponseDto> getTraceabilityByClientId();
    List<TraceabilityResponseDto> getTraceabilityByEmployeeId(String employeeId);
} 