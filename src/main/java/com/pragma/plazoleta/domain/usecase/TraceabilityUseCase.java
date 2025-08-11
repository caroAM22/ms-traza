package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.ITraceabilityServicePort;
import com.pragma.plazoleta.domain.model.OrderSummaryModel;
import com.pragma.plazoleta.domain.model.TraceabilityModel;
import com.pragma.plazoleta.domain.model.TraceabilityGroupedModel;
import com.pragma.plazoleta.domain.model.EmployeeAverageTimeModel;
import com.pragma.plazoleta.domain.spi.ITraceabilityPersistencePort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


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
    public List<OrderSummaryModel> getOrdersTraceabilityByRestaurantId(UUID restaurantId) {
        return getCompletedOrders(restaurantId);
    }

    @Override
    public List<EmployeeAverageTimeModel> getEmployeeTraceabilityByRestaurantId(UUID restaurantId) {
        List<OrderSummaryModel> completedOrders = getCompletedOrders(restaurantId);
        Map<UUID, List<OrderSummaryModel>> groupedByEmployeeId = completedOrders.stream()
                .filter(order -> order.getEmployeeId() != null)
                .collect(Collectors.groupingBy(order -> UUID.fromString(order.getEmployeeId())));

        return groupedByEmployeeId.entrySet().stream()
                .map(this::createEmployeeAverageTime)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted((e1, e2) -> e1.getAverageTime().compareTo(e2.getAverageTime()))
                .toList();
    }
    
    @Override
    public List<TraceabilityModel> getTraceabilityByOrderId(UUID orderId) {
        return traceabilityPersistencePort.findByOrderId(orderId);
    }
    
    @Override
    public List<TraceabilityGroupedModel> getTraceabilityByClientId(UUID clientId) {
        List<TraceabilityModel> allTraceability = traceabilityPersistencePort.findByClientId(clientId);
        Map<UUID, List<TraceabilityModel>> groupedByOrderId = allTraceability.stream()
                .collect(Collectors.groupingBy(TraceabilityModel::getOrderId));
        
        return groupedByOrderId.entrySet().stream()
                .map(entry -> TraceabilityGroupedModel.builder()
                        .orderId(entry.getKey())
                        .traceabilityList(entry.getValue())
                        .build())
                .toList();
    }

    private List<OrderSummaryModel> getCompletedOrders(UUID restaurantId) {
        List<TraceabilityModel> allTraceability = traceabilityPersistencePort.findByRestaurantId(restaurantId);
        Map<UUID, List<TraceabilityModel>> groupedByOrderId = allTraceability.stream()
                .collect(Collectors.groupingBy(TraceabilityModel::getOrderId));
        return groupedByOrderId.entrySet().stream()
                .map(this::createOrderSummary)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
    
    private Optional<OrderSummaryModel> createOrderSummary(Map.Entry<UUID, List<TraceabilityModel>> entry) {
        UUID orderId = entry.getKey();
        List<TraceabilityModel> traceabilityList = entry.getValue();
        traceabilityList.sort((t1, t2) -> t1.getDate().compareTo(t2.getDate()));
        if (!"PENDING".equals(traceabilityList.get(0).getNewState())) {
            return Optional.empty();
        }
        TraceabilityModel lastTrace = traceabilityList.get(traceabilityList.size() - 1);
        String finalStatus = lastTrace.getNewState();
        if (!"DELIVERED".equals(finalStatus)) {
            return Optional.empty();
        }
        
        LocalDateTime startTime = traceabilityList.get(0).getDate();
        LocalDateTime endTime = lastTrace.getDate();
        String employeeId = lastTrace.getEmployeeId() != null ? lastTrace.getEmployeeId().toString() : null;
        
        return Optional.of(OrderSummaryModel.builder()
                .orderId(orderId)
                .startTime(startTime)
                .endTime(endTime)
                .finalStatus(finalStatus)
                .employeeId(employeeId)
                .build());
    }
    
    private Optional<EmployeeAverageTimeModel> createEmployeeAverageTime(Map.Entry<UUID, List<OrderSummaryModel>> entry) {
        UUID employeeId = entry.getKey();
        List<OrderSummaryModel> orders = entry.getValue();
        long totalMillis = orders.stream()
            .mapToLong(order -> java.time.Duration.between(order.getStartTime(), order.getEndTime()).toMillis())
            .sum();
    
        double averageMillis = (double) totalMillis / orders.size();
        
        return Optional.of(EmployeeAverageTimeModel.builder()
                .employeeId(employeeId)
                .averageTime((long) averageMillis)
                .formattedAverageTime(String.format("%.0f ms", averageMillis))
                .build());
    }
} 