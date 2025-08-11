package com.pragma.plazoleta.application.mapper;

import com.pragma.plazoleta.application.dto.response.EmployeeAverageTimeResponse;
import com.pragma.plazoleta.domain.model.EmployeeAverageTimeModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IEmployeeAverageTimeMapper {
    EmployeeAverageTimeResponse toEmployeeAverageTimeResponse(EmployeeAverageTimeModel employeeAverageTimeModel);
    List<EmployeeAverageTimeResponse> toEmployeeAverageTimeResponseList(List<EmployeeAverageTimeModel> employeeAverageTimeModelList);
} 