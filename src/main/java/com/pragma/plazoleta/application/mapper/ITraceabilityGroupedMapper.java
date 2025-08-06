package com.pragma.plazoleta.application.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.pragma.plazoleta.application.dto.response.TraceabilityGroupedResponse;
import com.pragma.plazoleta.domain.model.TraceabilityGroupedModel;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ITraceabilityGroupedMapper {
    List<TraceabilityGroupedResponse> toTraceabilityGroupedResponseList(List<TraceabilityGroupedModel> traceabilityGroupedModelList);
}
