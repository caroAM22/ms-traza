package com.pragma.plazoleta.infrastructure.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pragma.plazoleta.domain.api.ITraceabilityServicePort;
import com.pragma.plazoleta.domain.spi.ITraceabilityPersistencePort;
import com.pragma.plazoleta.domain.usecase.TraceabilityUseCase;
import com.pragma.plazoleta.infrastructure.output.mongodb.adapter.TraceabilityPersistenceAdapter;
import com.pragma.plazoleta.infrastructure.output.mongodb.mapper.ITraceabilityDocumentMapper;
import com.pragma.plazoleta.infrastructure.output.mongodb.repository.ITraceabilityRepository;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final ITraceabilityRepository traceabilityRepository;
    private final ITraceabilityDocumentMapper traceabilityDocumentMapper;

    @Bean
    public ITraceabilityPersistencePort traceabilityPersistencePort() {
        return new TraceabilityPersistenceAdapter(traceabilityRepository, traceabilityDocumentMapper);
    }

    @Bean
    public ITraceabilityServicePort traceabilityServicePort() {
        return new TraceabilityUseCase(traceabilityPersistencePort());
    }
}