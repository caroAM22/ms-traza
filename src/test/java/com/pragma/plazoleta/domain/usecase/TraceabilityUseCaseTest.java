package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.model.TraceabilityModel;
import com.pragma.plazoleta.domain.model.OrderSummaryModel;
import com.pragma.plazoleta.domain.model.TraceabilityGroupedModel;
import com.pragma.plazoleta.domain.model.EmployeeAverageTimeModel;
import com.pragma.plazoleta.domain.spi.ITraceabilityPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TraceabilityUseCaseTest {

    @Mock
    private ITraceabilityPersistencePort traceabilityPersistencePort;

    @InjectMocks
    private TraceabilityUseCase traceabilityUseCase;

    private TraceabilityModel trace1;
    private TraceabilityModel trace2;
    private UUID testId;
    private UUID testOrderId;
    private UUID testClientId;
    private UUID testEmployeeId;
    private UUID testRestaurantId;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();
        testOrderId = UUID.randomUUID();
        testClientId = UUID.randomUUID();
        testEmployeeId = UUID.randomUUID();
        testRestaurantId = UUID.randomUUID();

        trace1 = TraceabilityModel.builder()
                .id(testId)
                .orderId(testOrderId)
                .clientId(testClientId)
                .clientEmail("client@example.com")
                .date(LocalDateTime.now().minusMinutes(10))
                .previousState("")
                .newState("PENDING")
                .employeeId(testEmployeeId)
                .employeeEmail("employee@example.com")
                .restaurantId(testRestaurantId)
                .build();

        trace2 = TraceabilityModel.builder()
                .id(testId)
                .orderId(testOrderId)
                .clientId(testClientId)
                .clientEmail("client@example.com")
                .date(LocalDateTime.now())
                .previousState("PENDING")
                .newState("DELIVERED")
                .employeeId(testEmployeeId)
                .employeeEmail("employee@example.com")
                .restaurantId(testRestaurantId)
                .build();
    }

    @Test
    void saveTraceabilityShouldSetCreationDateAndReturnSavedModel() {
        when(traceabilityPersistencePort.saveTraceability(any(TraceabilityModel.class)))
                .thenReturn(trace1);
        TraceabilityModel result = traceabilityUseCase.saveTraceability(trace1);

        assertNotNull(result);
        assertNotNull(trace1.getDate());
        assertEquals(trace1.getId(), result.getId());
        assertEquals(trace1.getOrderId(), result.getOrderId());
        verify(traceabilityPersistencePort).saveTraceability(trace1);
    }

    @Test
    void getOrdersTraceabilityByRestaurantIdShouldReturnOrderSummaries() {
        List<TraceabilityModel> traceabilityList = Arrays.asList(trace1, trace2);
        
        when(traceabilityPersistencePort.findByRestaurantId(testRestaurantId)).thenReturn(traceabilityList);
        List<OrderSummaryModel> result = traceabilityUseCase.getOrdersTraceabilityByRestaurantId(testRestaurantId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testOrderId, result.get(0).getOrderId());
        assertEquals("DELIVERED", result.get(0).getFinalStatus());
        assertEquals(testEmployeeId.toString(), result.get(0).getEmployeeId());
        verify(traceabilityPersistencePort).findByRestaurantId(testRestaurantId);
    }

    @Test
    void getTraceabilityByOrderIdShouldReturnTraceabilityRecords() {
        List<TraceabilityModel> expectedList = Arrays.asList(trace1, trace2);

        when(traceabilityPersistencePort.findByOrderId(testOrderId)).thenReturn(expectedList);
        List<TraceabilityModel> result = traceabilityUseCase.getTraceabilityByOrderId(testOrderId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(trace1, result.get(0));
        verify(traceabilityPersistencePort).findByOrderId(testOrderId);
    }

    @Test
    void getTraceabilityByClientIdShouldReturnGroupedTraceability() {
        List<TraceabilityModel> expectedList = Arrays.asList(trace1, trace2);

        when(traceabilityPersistencePort.findByClientId(testClientId)).thenReturn(expectedList);
        List<TraceabilityGroupedModel> result = traceabilityUseCase.getTraceabilityByClientId(testClientId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testOrderId, result.get(0).getOrderId());
        assertEquals(2, result.get(0).getTraceabilityList().size());
        verify(traceabilityPersistencePort).findByClientId(testClientId);
    }

    @Test
    void getEmployeeTraceabilityByRestaurantIdShouldReturnEmployeeRanking() {
        List<TraceabilityModel> traceabilityList = Arrays.asList(trace1, trace2);
        
        when(traceabilityPersistencePort.findByRestaurantId(testRestaurantId)).thenReturn(traceabilityList);
        List<EmployeeAverageTimeModel> result = traceabilityUseCase.getEmployeeTraceabilityByRestaurantId(testRestaurantId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testEmployeeId, result.get(0).getEmployeeId());
        assertNotNull(result.get(0).getAverageTime());
        assertTrue(result.get(0).getFormattedAverageTime().contains("ms"));
        verify(traceabilityPersistencePort).findByRestaurantId(testRestaurantId);
    }

    @Test
    void getEmployeeTraceabilityByRestaurantIdShouldFilterIncompleteOrders() {
        List<TraceabilityModel> traceabilityList = Arrays.asList(trace1);
        
        when(traceabilityPersistencePort.findByRestaurantId(testRestaurantId)).thenReturn(traceabilityList);
        List<EmployeeAverageTimeModel> result = traceabilityUseCase.getEmployeeTraceabilityByRestaurantId(testRestaurantId);

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(traceabilityPersistencePort).findByRestaurantId(testRestaurantId);
    }

    @Test
    void getEmployeeTraceabilityByRestaurantIdShouldReturnEmptyListWhenTheFirstStateIsNotPending() {
        List<TraceabilityModel> traceabilityList = Arrays.asList(trace2);
        
        when(traceabilityPersistencePort.findByRestaurantId(testRestaurantId)).thenReturn(traceabilityList);
        List<EmployeeAverageTimeModel> result = traceabilityUseCase.getEmployeeTraceabilityByRestaurantId(testRestaurantId);

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(traceabilityPersistencePort).findByRestaurantId(testRestaurantId);
    }

    @Test
    void getEmployeeTraceabilityByRestaurantIdShouldReturnEmptyListWhenTraceabilityListNotHaveEmployeeId() {
        trace1.setEmployeeId(null);
        trace2.setEmployeeId(null);
        List<TraceabilityModel> traceabilityList = Arrays.asList(trace1, trace2);
        
        when(traceabilityPersistencePort.findByRestaurantId(testRestaurantId)).thenReturn(traceabilityList);
        List<EmployeeAverageTimeModel> result = traceabilityUseCase.getEmployeeTraceabilityByRestaurantId(testRestaurantId);

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(traceabilityPersistencePort).findByRestaurantId(testRestaurantId);
    }


    @Test
    void getEmployeeRankingShouldArrangeAscending() {
        UUID employeeId = UUID.randomUUID();
        UUID order2Id = UUID.randomUUID();
        TraceabilityModel trace3 = TraceabilityModel.builder()
                .id(testId)
                .orderId(order2Id)
                .clientId(testClientId)
                .clientEmail("client@example.com")
                .date(LocalDateTime.now().minusMinutes(20))
                .previousState("")
                .newState("PENDING")
                .employeeId(employeeId)
                .employeeEmail("employee@example.com")
                .restaurantId(testRestaurantId)
                .build();

        TraceabilityModel trace4 = TraceabilityModel.builder()
                .id(testId)
                .orderId(order2Id)
                .clientId(testClientId)
                .clientEmail("client@example.com")
                .date(LocalDateTime.now())
                .previousState("PENDING")
                .newState("DELIVERED")
                .employeeId(employeeId)
                .employeeEmail("employee@example.com")
                .restaurantId(testRestaurantId)
                .build();
        List<TraceabilityModel> traceabilityList = Arrays.asList(trace3, trace4, trace1, trace2);
        
        when(traceabilityPersistencePort.findByRestaurantId(testRestaurantId)).thenReturn(traceabilityList);
        List<EmployeeAverageTimeModel> result = traceabilityUseCase.getEmployeeTraceabilityByRestaurantId(testRestaurantId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(testEmployeeId, result.get(0).getEmployeeId());
        assertEquals(employeeId, result.get(1).getEmployeeId());
        assertEquals("600000 ms", result.get(0).getFormattedAverageTime());
        assertEquals("1200000 ms", result.get(1).getFormattedAverageTime());
        verify(traceabilityPersistencePort).findByRestaurantId(testRestaurantId);
    }
} 