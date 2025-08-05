package com.pragma.plazoleta.infrastructure.output.mongodb.repository;

import com.pragma.plazoleta.infrastructure.output.mongodb.entity.TraceabilityDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITraceabilityRepository extends MongoRepository<TraceabilityDocument, String> {
    List<TraceabilityDocument> findByOrderIdAndClientId(String orderId, String clientId);
    List<TraceabilityDocument> findByClientId(String clientId);
    List<TraceabilityDocument> findByEmployeeId(String employeeId);
    List<TraceabilityDocument> findByRestaurantId(String restaurantId);
} 