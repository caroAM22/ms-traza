package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.ITraceabilityServicePort;
import com.pragma.plazoleta.domain.exception.DomainException;
import com.pragma.plazoleta.domain.model.TraceabilityModel;
import com.pragma.plazoleta.domain.spi.ISecurityContextPort;
import com.pragma.plazoleta.domain.spi.ITraceabilityPersistencePort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TraceabilityUseCase implements ITraceabilityServicePort {
    
    private final ITraceabilityPersistencePort traceabilityPersistencePort;
    private final ISecurityContextPort securityContextPort;
    
    @Override
    public TraceabilityModel saveTraceability(TraceabilityModel traceabilityModel) {
        traceabilityModel.setDate(LocalDateTime.now());
        traceabilityModel.setId(UUID.randomUUID());
        return traceabilityPersistencePort.saveTraceability(traceabilityModel);
    }
    
    @Override
    public List<TraceabilityModel> getTraceabilityByRestaurantId(UUID restaurantId) {
        validateUserRole("OWNER");
        return traceabilityPersistencePort.findByRestaurantId(restaurantId);
    }
    
    @Override
    public List<TraceabilityModel> getTraceabilityByOrderId(UUID orderId) {
        validateUserRole("CUSTOMER");
        return traceabilityPersistencePort.findByOrderIdAndClientId(orderId, securityContextPort.getUserIdOfUserAutenticated());
    }
    
    @Override
    public List<TraceabilityModel> getTraceabilityByClientId() {
        validateUserRole("CUSTOMER");
        return traceabilityPersistencePort.findByClientId(securityContextPort.getUserIdOfUserAutenticated());
    }
    
    @Override
    public List<TraceabilityModel> getTraceabilityByEmployeeId(UUID employeeId) {
        validateUserRole("OWNER");
        return traceabilityPersistencePort.findByEmployeeId(employeeId);
    }

    private void validateUserRole(String role) {
        if (!securityContextPort.getRoleOfUserAutenticated().equals(role)) {
            throw new DomainException("You are not authorized to access this resource");
        }
    }
} 