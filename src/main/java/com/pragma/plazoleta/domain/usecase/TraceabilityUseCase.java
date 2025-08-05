package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.ITraceabilityServicePort;
import com.pragma.plazoleta.domain.model.TraceabilityModel;
import com.pragma.plazoleta.domain.spi.ITraceabilityPersistencePort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class TraceabilityUseCase implements ITraceabilityServicePort {
    
    private final ITraceabilityPersistencePort traceabilityPersistencePort;
    
    @Override
    public TraceabilityModel saveTraceability(TraceabilityModel traceabilityModel) {
        traceabilityModel.setDate(LocalDateTime.now());
        traceabilityModel.setId(UUID.randomUUID());
        return traceabilityPersistencePort.saveTraceability(traceabilityModel);
    }
    
    @Override
    public List<TraceabilityModel> getAllTraceability() {
        return traceabilityPersistencePort.getAllTraceability();
    }
    
    @Override
    public List<TraceabilityModel> getTraceabilityByOrderId(UUID orderId) {
        return traceabilityPersistencePort.findByOrderId(orderId);
    }
    
    @Override
    public List<TraceabilityModel> getTraceabilityByClientId(UUID clientId) {
        return traceabilityPersistencePort.findByClientId(clientId);
    }
    
    @Override
    public List<TraceabilityModel> getTraceabilityByEmployeeId(UUID employeeId) {
        return traceabilityPersistencePort.findByEmployeeId(employeeId);
    }
} 