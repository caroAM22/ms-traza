package com.pragma.plazoleta.infrastructure.output.mongodb.adapter;

import com.pragma.plazoleta.domain.model.TraceabilityModel;
import com.pragma.plazoleta.domain.spi.ITraceabilityPersistencePort;
import com.pragma.plazoleta.infrastructure.output.mongodb.mapper.ITraceabilityDocumentMapper;
import com.pragma.plazoleta.infrastructure.output.mongodb.repository.ITraceabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TraceabilityPersistenceAdapter implements ITraceabilityPersistencePort {
    
    private final ITraceabilityRepository traceabilityRepository;
    private final ITraceabilityDocumentMapper traceabilityDocumentMapper;
    
    @Override
    public TraceabilityModel saveTraceability(TraceabilityModel traceabilityModel) {
        var traceabilityDocument = traceabilityDocumentMapper.toTraceabilityDocument(traceabilityModel);
        var savedDocument = traceabilityRepository.save(traceabilityDocument);
        return traceabilityDocumentMapper.toTraceabilityModel(savedDocument);
    }
    
    @Override
    public List<TraceabilityModel> findByRestaurantId(UUID restaurantId) {
        var documents = traceabilityRepository.findByRestaurantId(restaurantId.toString());
        return traceabilityDocumentMapper.toTraceabilityModelList(documents);
    }
    
    @Override
    public List<TraceabilityModel> findByOrderIdAndClientId(UUID orderId, UUID clientId) {
        var documents = traceabilityRepository.findByOrderIdAndClientId(orderId.toString(), clientId.toString());
        return traceabilityDocumentMapper.toTraceabilityModelList(documents);
    }
    
    @Override
    public List<TraceabilityModel> findByClientId(UUID clientId) {
        var documents = traceabilityRepository.findByClientId(clientId.toString());
        return traceabilityDocumentMapper.toTraceabilityModelList(documents);
    }
    
    @Override
    public List<TraceabilityModel> findByEmployeeId(UUID employeeId) {
        var documents = traceabilityRepository.findByEmployeeId(employeeId.toString());
        return traceabilityDocumentMapper.toTraceabilityModelList(documents);
    }
} 