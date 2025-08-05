package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.model.TraceabilityModel;
import com.pragma.plazoleta.domain.spi.ITraceabilityPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
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

    private TraceabilityModel traceabilityModel;
    private UUID testId;
    private UUID testOrderId;
    private UUID testClientId;
    private UUID testEmployeeId;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();
        testOrderId = UUID.randomUUID();
        testClientId = UUID.randomUUID();
        testEmployeeId = UUID.randomUUID();
        
        traceabilityModel = TraceabilityModel.builder()
                .id(testId)
                .orderId(testOrderId)
                .clientId(testClientId)
                .clientEmail("client@example.com")
                .date(LocalDateTime.now())
                .previousState("PENDING")
                .newState("IN_PROGRESS")
                .employeeId(testEmployeeId)
                .employeeEmail("employee@example.com")
                .build();
    }

    @Test
    void saveTraceability_ShouldSetCreationDateAndReturnSavedModel() {
        TraceabilityModel inputModel = TraceabilityModel.builder()
                .orderId(testOrderId)
                .clientId(testClientId)
                .clientEmail("client@example.com")
                .previousState("PENDING")
                .newState("IN_PROGRESS")
                .employeeId(testEmployeeId)
                .employeeEmail("employee@example.com")
                .build();

        when(traceabilityPersistencePort.saveTraceability(any(TraceabilityModel.class)))
                .thenReturn(traceabilityModel);
        TraceabilityModel result = traceabilityUseCase.saveTraceability(inputModel);

        assertNotNull(result);
        assertNotNull(inputModel.getDate());
        assertEquals(traceabilityModel.getId(), result.getId());
        assertEquals(traceabilityModel.getOrderId(), result.getOrderId());
        verify(traceabilityPersistencePort).saveTraceability(inputModel);
    }

    @Test
    void getAllTraceability_ShouldReturnAllTraceabilityRecords() {
        List<TraceabilityModel> expectedList = Arrays.asList(traceabilityModel);
        
        when(traceabilityPersistencePort.getAllTraceability()).thenReturn(expectedList);
        List<TraceabilityModel> result = traceabilityUseCase.getAllTraceability();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(traceabilityModel, result.get(0));
        verify(traceabilityPersistencePort).getAllTraceability();
    }

    @Test
    void getAllTraceabilityShouldReturnEmptyListWhenNoRecordsExist() {
        when(traceabilityPersistencePort.getAllTraceability()).thenReturn(Collections.emptyList());
        List<TraceabilityModel> result = traceabilityUseCase.getAllTraceability();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(traceabilityPersistencePort).getAllTraceability();
    }

    @Test
    void getTraceabilityByOrderIdShouldReturnTraceabilityRecords() {
        List<TraceabilityModel> expectedList = Arrays.asList(traceabilityModel);

        when(traceabilityPersistencePort.findByOrderId(testOrderId)).thenReturn(expectedList);
        List<TraceabilityModel> result = traceabilityUseCase.getTraceabilityByOrderId(testOrderId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(traceabilityModel, result.get(0));
        verify(traceabilityPersistencePort).findByOrderId(testOrderId);
    }

    @Test
    void getTraceabilityByOrderIdShouldReturnEmptyListWhenNoRecordsFound() {
        UUID nonExistentOrderId = UUID.randomUUID();

        when(traceabilityPersistencePort.findByOrderId(nonExistentOrderId)).thenReturn(Collections.emptyList());
        List<TraceabilityModel> result = traceabilityUseCase.getTraceabilityByOrderId(nonExistentOrderId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(traceabilityPersistencePort).findByOrderId(nonExistentOrderId);
    }

    @Test
    void getTraceabilityByClientId_ShouldReturnTraceabilityRecords() {
        List<TraceabilityModel> expectedList = Arrays.asList(traceabilityModel);

        when(traceabilityPersistencePort.findByClientId(testClientId)).thenReturn(expectedList);
        List<TraceabilityModel> result = traceabilityUseCase.getTraceabilityByClientId(testClientId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(traceabilityModel, result.get(0));
        verify(traceabilityPersistencePort).findByClientId(testClientId);
    }

    @Test
    void getTraceabilityByClientIdShouldReturnEmptyListWhenNoRecordsFound() {
        UUID nonExistentClientId = UUID.randomUUID();

        when(traceabilityPersistencePort.findByClientId(nonExistentClientId)).thenReturn(Collections.emptyList());
        List<TraceabilityModel> result = traceabilityUseCase.getTraceabilityByClientId(nonExistentClientId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(traceabilityPersistencePort).findByClientId(nonExistentClientId);
    }

    @Test
    void getTraceabilityByEmployeeIdShouldReturnTraceabilityRecords() {
        List<TraceabilityModel> expectedList = Arrays.asList(traceabilityModel);

        when(traceabilityPersistencePort.findByEmployeeId(testEmployeeId)).thenReturn(expectedList);
        List<TraceabilityModel> result = traceabilityUseCase.getTraceabilityByEmployeeId(testEmployeeId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(traceabilityModel, result.get(0));
        verify(traceabilityPersistencePort).findByEmployeeId(testEmployeeId);
    }

    @Test
    void getTraceabilityByEmployeeIdShouldReturnEmptyListWhenNoRecordsFound() {
        UUID nonExistentEmployeeId = UUID.randomUUID();

        when(traceabilityPersistencePort.findByEmployeeId(nonExistentEmployeeId)).thenReturn(Collections.emptyList());
        List<TraceabilityModel> result = traceabilityUseCase.getTraceabilityByEmployeeId(nonExistentEmployeeId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(traceabilityPersistencePort).findByEmployeeId(nonExistentEmployeeId);
    }
} 