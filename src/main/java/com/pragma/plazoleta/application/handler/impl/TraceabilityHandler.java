package com.pragma.plazoleta.application.handler.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.pragma.plazoleta.application.dto.request.TraceabilityRequestDto;
import com.pragma.plazoleta.application.dto.response.TraceabilityResponseDto;
import com.pragma.plazoleta.application.handler.ITraceabilityHandler;
import com.pragma.plazoleta.application.mapper.ITraceabilityRequestMapper;
import com.pragma.plazoleta.application.mapper.ITraceabilityResponseMapper;
import com.pragma.plazoleta.domain.api.ITraceabilityServicePort;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TraceabilityHandler implements ITraceabilityHandler {
    
    private final ITraceabilityServicePort traceabilityServicePort;
    private final ITraceabilityRequestMapper traceabilityRequestMapper;
    private final ITraceabilityResponseMapper traceabilityResponseMapper;
    
    @Override
    public TraceabilityResponseDto saveTraceability(TraceabilityRequestDto traceabilityRequestDto) {
        var traceabilityModel = traceabilityRequestMapper.toTraceabilityModel(traceabilityRequestDto);
        var savedTraceability = traceabilityServicePort.saveTraceability(traceabilityModel);
        return traceabilityResponseMapper.toTraceabilityResponseDto(savedTraceability);
    }
    
    @Override
    public List<TraceabilityResponseDto> getAllTraceability() {
        var traceabilityList = traceabilityServicePort.getAllTraceability();
        return traceabilityResponseMapper.toTraceabilityResponseDtoList(traceabilityList);
    }
    
    @Override
    public List<TraceabilityResponseDto> getTraceabilityByOrderId(String orderId) {
        var traceabilityList = traceabilityServicePort.getTraceabilityByOrderId(UUID.fromString(orderId));
        return traceabilityResponseMapper.toTraceabilityResponseDtoList(traceabilityList);
    }
    
    @Override
    public List<TraceabilityResponseDto> getTraceabilityByClientId(String clientId) {
        var traceabilityList = traceabilityServicePort.getTraceabilityByClientId(UUID.fromString(clientId));
        return traceabilityResponseMapper.toTraceabilityResponseDtoList(traceabilityList);
    }
    
    @Override
    public List<TraceabilityResponseDto> getTraceabilityByEmployeeId(String employeeId) {
        var traceabilityList = traceabilityServicePort.getTraceabilityByEmployeeId(UUID.fromString(employeeId));
        return traceabilityResponseMapper.toTraceabilityResponseDtoList(traceabilityList);
    }
} 