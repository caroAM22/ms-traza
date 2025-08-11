package com.pragma.plazoleta.infrastructure.output.mongodb.repository;

import com.pragma.plazoleta.infrastructure.output.mongodb.entity.TraceabilityDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITraceabilityRepository extends MongoRepository<TraceabilityDocument, String> {
    List<TraceabilityDocument> findByOrderId(String orderId);
    List<TraceabilityDocument> findByClientId(String clientId);
    List<TraceabilityDocument> findByRestaurantId(String restaurantId);
} 