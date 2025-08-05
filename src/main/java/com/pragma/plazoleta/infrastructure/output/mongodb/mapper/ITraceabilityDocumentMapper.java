package com.pragma.plazoleta.infrastructure.output.mongodb.mapper;

import com.pragma.plazoleta.domain.model.TraceabilityModel;
import com.pragma.plazoleta.infrastructure.output.mongodb.entity.TraceabilityDocument;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ITraceabilityDocumentMapper {
    TraceabilityDocument toTraceabilityDocument(TraceabilityModel traceabilityModel);
    TraceabilityModel toTraceabilityModel(TraceabilityDocument traceabilityDocument);
    List<TraceabilityModel> toTraceabilityModelList(List<TraceabilityDocument> traceabilityDocumentList);
} 