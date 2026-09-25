package com.accenture.ems.emstraining.mapper;

import com.accenture.ems.emstraining.entity.InternStaffing;
import com.accenture.ems.emstraining.model.InternStaffingRequest;
import com.accenture.ems.emstraining.model.InternStaffingResponse;
import com.accenture.ems.emstraining.model.InternStaffingSummaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InternStaffingMapper {

    List<InternStaffingSummaryResponse> toResponseList(List<InternStaffing> staffings);

    @Mapping(source = "employee.employeeId", target = "employeeId")
    @Mapping(source = "employee.surname", target = "employeeSurname")
    @Mapping(source = "internHiringStatus.status", target = "internHiringStatus")
    InternStaffingSummaryResponse toSummaryResponse(InternStaffing staffing);

    InternStaffingResponse toResponse(InternStaffing staffing);

    InternStaffing toEntity(InternStaffingRequest internStaffingRequest);
}