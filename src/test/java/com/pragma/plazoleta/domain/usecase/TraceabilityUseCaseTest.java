package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.exception.DomainException;
import com.pragma.plazoleta.domain.model.TraceabilityModel;
import com.pragma.plazoleta.domain.spi.ISecurityContextPort;
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

    @Mock
    private ISecurityContextPort securityContextPort;

    private TraceabilityModel traceabilityModel;
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
                .restaurantId(testRestaurantId)
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
    void getTraceabilityByRestaurantIdShouldReturnAllTraceabilityRecords() {
        List<TraceabilityModel> expectedList = Arrays.asList(traceabilityModel);
        
        when(traceabilityPersistencePort.findByRestaurantId(testRestaurantId)).thenReturn(expectedList);
        when(securityContextPort.getRoleOfUserAutenticated()).thenReturn("OWNER");
        List<TraceabilityModel> result = traceabilityUseCase.getTraceabilityByRestaurantId(testRestaurantId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(traceabilityModel, result.get(0));
        verify(traceabilityPersistencePort).findByRestaurantId(testRestaurantId);
    }

    @Test
    void getTraceabilityByOrderIdShouldReturnTraceabilityRecords() {
        List<TraceabilityModel> expectedList = Arrays.asList(traceabilityModel);

        when(traceabilityPersistencePort.findByOrderIdAndClientId(testOrderId, testClientId)).thenReturn(expectedList);
        when(securityContextPort.getRoleOfUserAutenticated()).thenReturn("CUSTOMER");
        when(securityContextPort.getUserIdOfUserAutenticated()).thenReturn(testClientId);
        List<TraceabilityModel> result = traceabilityUseCase.getTraceabilityByOrderId(testOrderId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(traceabilityModel, result.get(0));
        verify(traceabilityPersistencePort).findByOrderIdAndClientId(testOrderId, testClientId);
    }

    @Test
    void getTraceabilityByClientIdShouldReturnTraceabilityRecords() {
        List<TraceabilityModel> expectedList = Arrays.asList(traceabilityModel);

        when(traceabilityPersistencePort.findByClientId(testClientId)).thenReturn(expectedList);
        when(securityContextPort.getRoleOfUserAutenticated()).thenReturn("CUSTOMER");
        when(securityContextPort.getUserIdOfUserAutenticated()).thenReturn(testClientId);
        List<TraceabilityModel> result = traceabilityUseCase.getTraceabilityByClientId();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(traceabilityModel, result.get(0));
        verify(traceabilityPersistencePort).findByClientId(testClientId);
    }

    @Test
    void getTraceabilityByEmployeeIdShouldReturnTraceabilityRecords() {
        List<TraceabilityModel> expectedList = Arrays.asList(traceabilityModel);

        when(traceabilityPersistencePort.findByEmployeeId(testEmployeeId)).thenReturn(expectedList);
        when(securityContextPort.getRoleOfUserAutenticated()).thenReturn("OWNER");
        List<TraceabilityModel> result = traceabilityUseCase.getTraceabilityByEmployeeId(testEmployeeId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(traceabilityModel, result.get(0));
        verify(traceabilityPersistencePort).findByEmployeeId(testEmployeeId);
    }

    @Test
    void validateUserRoleShouldThrowExceptionWhenUserIsNotOwner() {
        when(securityContextPort.getRoleOfUserAutenticated()).thenReturn("CUSTOMER");
    
        assertThrows(DomainException.class, () -> traceabilityUseCase.getTraceabilityByRestaurantId(testRestaurantId));
        verify(securityContextPort).getRoleOfUserAutenticated();
    }

    @Test
    void validateUserRoleShouldNotThrowExceptionWhenUserIsOwner() {
        when(securityContextPort.getRoleOfUserAutenticated()).thenReturn("OWNER");
        traceabilityUseCase.getTraceabilityByRestaurantId(testRestaurantId);
        verify(traceabilityPersistencePort).findByRestaurantId(testRestaurantId);
        verify(securityContextPort).getRoleOfUserAutenticated();
    }
} 