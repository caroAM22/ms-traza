package com.pragma.plazoleta.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.pragma.plazoleta.application.dto.response.TraceabilityResponseDto;
import com.pragma.plazoleta.domain.model.TraceabilityModel;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ITraceabilityResponseMapper {
    TraceabilityResponseDto toTraceabilityResponseDto(TraceabilityModel traceabilityModel);
    List<TraceabilityResponseDto> toTraceabilityResponseDtoList(List<TraceabilityModel> traceabilityModelList);
} 