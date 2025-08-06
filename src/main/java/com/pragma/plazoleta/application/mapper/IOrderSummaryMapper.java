package com.pragma.plazoleta.application.mapper;

import com.pragma.plazoleta.application.dto.response.OrderSummaryResponse;
import com.pragma.plazoleta.domain.model.OrderSummaryModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderSummaryMapper {
    OrderSummaryResponse toOrderSummaryResponse(OrderSummaryModel orderSummaryModel);
    List<OrderSummaryResponse> toOrderSummaryResponseList(List<OrderSummaryModel> orderSummaryModelList);
} 