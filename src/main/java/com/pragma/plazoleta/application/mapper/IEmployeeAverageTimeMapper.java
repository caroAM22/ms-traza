package com.pragma.plazoleta.application.mapper;

import com.pragma.plazoleta.application.dto.response.EmployeeAverageTimeResponse;
import com.pragma.plazoleta.domain.model.EmployeeAverageTimeModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IEmployeeAverageTimeMapper {
    @Mapping(target = "averageTime", source = "employeeAverageTimeModel")
    EmployeeAverageTimeResponse toEmployeeAverageTimeResponse(EmployeeAverageTimeModel employeeAverageTimeModel);
    List<EmployeeAverageTimeResponse> toEmployeeAverageTimeResponseList(List<EmployeeAverageTimeModel> employeeAverageTimeModelList);
    
    default String mapAverageTime(EmployeeAverageTimeModel model) {
        return model.getFormattedAverageTime();
    }
} 