package com.accenture.ems.emstraining.mapper;

import com.accenture.ems.emstraining.entity.Employee;
import com.accenture.ems.emstraining.entity.InternHiringStatusEntity;
import com.accenture.ems.emstraining.entity.InternStaffing;
import com.accenture.ems.emstraining.model.InternStaffingPatchRequest;
import com.accenture.ems.emstraining.model.InternStaffingRequest;
import com.accenture.ems.emstraining.model.InternStaffingResponse;
import com.accenture.ems.emstraining.model.InternStaffingSummaryResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InternStaffingMapper {

    List<InternStaffingSummaryResponse> toResponseList(List<InternStaffing> staffings);

    InternStaffingSummaryResponse toSummaryResponse(InternStaffing staffing);

    @Mapping(source = "staffing.id", target = "id")
    @Mapping(source = "employee", target = "employee")
    @Mapping(source = "internHiringStatus", target = "internHiringStatus")
    InternStaffingResponse toResponse(InternStaffing staffing, Employee employee, InternHiringStatusEntity internHiringStatus);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employeeId", ignore = true)
    @Mapping(target = "internHiringStatusId", ignore = true)
    InternStaffing toEntity(InternStaffingRequest internStaffingRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employeeId", ignore = true)
    @Mapping(target = "internHiringStatusId", ignore = true)
    void updateStaffing(InternStaffingPatchRequest internStaffingRequest, @MappingTarget InternStaffing staffing);
}