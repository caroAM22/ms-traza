package com.pragma.plazoleta.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.pragma.plazoleta.application.dto.request.TraceabilityRequest;
import com.pragma.plazoleta.application.dto.response.TraceabilityResponse;
import com.pragma.plazoleta.domain.model.TraceabilityModel;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ITraceabilityMapper {
    TraceabilityResponse toTraceabilityResponse(TraceabilityModel traceabilityModel);
    List<TraceabilityResponse> toTraceabilityResponseList(List<TraceabilityModel> traceabilityModelList);
    TraceabilityModel toTraceabilityModel(TraceabilityRequest traceabilityRequestDto);
} 