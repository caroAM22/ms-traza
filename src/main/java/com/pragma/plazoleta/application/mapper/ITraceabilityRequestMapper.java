package com.pragma.plazoleta.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.pragma.plazoleta.application.dto.request.TraceabilityRequestDto;
import com.pragma.plazoleta.domain.model.TraceabilityModel;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ITraceabilityRequestMapper {
    TraceabilityModel toTraceabilityModel(TraceabilityRequestDto traceabilityRequestDto);
} 