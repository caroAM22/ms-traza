package com.pragma.plazoleta.application.handler.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.pragma.plazoleta.application.dto.request.TraceabilityRequest;
import com.pragma.plazoleta.application.dto.response.TraceabilityResponse;
import com.pragma.plazoleta.application.dto.response.TraceabilityGroupedResponse;
import com.pragma.plazoleta.application.dto.response.OrderSummaryResponse;
import com.pragma.plazoleta.application.dto.response.EmployeeAverageTimeResponse;
import com.pragma.plazoleta.application.handler.ITraceabilityHandler;
import com.pragma.plazoleta.application.mapper.ITraceabilityMapper;
import com.pragma.plazoleta.application.mapper.ITraceabilityGroupedMapper;
import com.pragma.plazoleta.application.mapper.IOrderSummaryMapper;
import com.pragma.plazoleta.application.mapper.IEmployeeAverageTimeMapper;
import com.pragma.plazoleta.domain.api.ITraceabilityServicePort;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TraceabilityHandler implements ITraceabilityHandler {
    
    private final ITraceabilityServicePort traceabilityServicePort;
    private final ITraceabilityMapper traceabilityMapper;
    private final ITraceabilityGroupedMapper traceabilityGroupedMapper;
    private final IOrderSummaryMapper orderSummaryMapper;
    private final IEmployeeAverageTimeMapper employeeAverageTimeMapper;
    
    @Override
    public TraceabilityResponse saveTraceability(TraceabilityRequest traceabilityRequestDto) {
        var traceabilityModel = traceabilityMapper.toTraceabilityModel(traceabilityRequestDto);
        var savedTraceability = traceabilityServicePort.saveTraceability(traceabilityModel);
        return traceabilityMapper.toTraceabilityResponse(savedTraceability);
    }
    
    @Override
    public List<OrderSummaryResponse> getOrdersTraceabilityByRestaurantId(String restaurantId) {
        var orderSummaryList = traceabilityServicePort.getOrdersTraceabilityByRestaurantId(UUID.fromString(restaurantId));
        return orderSummaryMapper.toOrderSummaryResponseList(orderSummaryList);
    }
    
    @Override
    public List<TraceabilityResponse> getTraceabilityByOrderId(String orderId) {
        var traceabilityList = traceabilityServicePort.getTraceabilityByOrderId(UUID.fromString(orderId));
        return traceabilityMapper.toTraceabilityResponseList(traceabilityList);
    }
    
    @Override
    public List<TraceabilityGroupedResponse> getTraceabilityByClientId(String clientId) {
        var traceabilityGroupedList = traceabilityServicePort.getTraceabilityByClientId(UUID.fromString(clientId));
        return traceabilityGroupedMapper.toTraceabilityGroupedResponseList(traceabilityGroupedList);
    }
    
    @Override
    public List<EmployeeAverageTimeResponse> getEmployeeRankingByRestaurantId(String restaurantId) {
        var employeeAverageTimeList = traceabilityServicePort.getEmployeeTraceabilityByRestaurantId(UUID.fromString(restaurantId));
        return employeeAverageTimeMapper.toEmployeeAverageTimeResponseList(employeeAverageTimeList);
    }
} 