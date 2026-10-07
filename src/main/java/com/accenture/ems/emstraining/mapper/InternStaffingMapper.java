package com.accenture.ems.emstraining.mapper;

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

    //    @Mapping(source = "employee.employeeId", target = "employeeId")
//    @Mapping(source = "employee", target = "fullName", qualifiedByName = "surnameCommaName")
//    @Mapping(source = "internHiringStatus.status", target = "internHiringStatus")
    InternStaffingSummaryResponse toSummaryResponse(InternStaffing staffing);

    InternStaffingResponse toResponse(InternStaffing staffing);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employeeId", ignore = true)
    @Mapping(target = "internHiringStatusId", ignore = true)
    InternStaffing toEntity(InternStaffingRequest internStaffingRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employeeId", ignore = true)
    @Mapping(target = "internHiringStatusId", ignore = true)
    void updateStaffing(InternStaffingPatchRequest internStaffingRequest, @MappingTarget InternStaffing staffing);

//    @Named("surnameCommaName")
//    default String fullName(Employee employee) {
//        return String.format("%s, %s", employee.getSurname(), employee.getName());
//    }
}