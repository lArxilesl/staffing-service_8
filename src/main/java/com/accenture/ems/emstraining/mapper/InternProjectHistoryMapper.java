package com.accenture.ems.emstraining.mapper;

import com.accenture.ems.emstraining.entity.*;
import com.accenture.ems.emstraining.model.InternProjectHistoryRequestDto;
import com.accenture.ems.emstraining.model.InternProjectHistoryResponseDto;
import com.accenture.ems.emstraining.repository.EmployeeRepository;
import com.accenture.ems.emstraining.repository.InternStaffingRepository;
import com.accenture.ems.emstraining.repository.ProjectHiringStatusRepository;
import com.accenture.ems.emstraining.repository.ProjectRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class InternProjectHistoryMapper {

    @Autowired
    protected InternStaffingRepository internStaffingRepository;
    @Autowired
    protected ProjectRepository projectRepository;
    @Autowired
    protected EmployeeRepository employeeRepository;
    @Autowired
    protected ProjectHiringStatusRepository projectHiringStatusRepository;

    @Mapping(source = "internStaffing.id", target = "internStaffingId")
    @Mapping(source = "project.projectId", target = "projectId")
    @Mapping(source = "responsiblePerson.employeeId", target = "responsiblePersonId")
    @Mapping(source = "projectHiringStatus.id", target = "projectHiringStatusId")
    public abstract InternProjectHistoryResponseDto internProjectHistoryEntityToDTO(InternProjectHistory entity);

    @Mapping(source = "internStaffingId", target = "internStaffing", qualifiedByName = "internStaffingFromId")
    @Mapping(source = "projectId", target = "project", qualifiedByName = "projectFromId")
    @Mapping(source = "responsiblePersonId", target = "responsiblePerson", qualifiedByName = "employeeFromId")
    @Mapping(source = "projectHiringStatusId", target = "projectHiringStatus", qualifiedByName = "projectHiringStatusFromId")
    public abstract InternProjectHistory internProjectHistoryDTOtoEntity(InternProjectHistoryRequestDto dto);

    @Mapping(source = "internStaffingId", target = "internStaffing", qualifiedByName = "internStaffingFromId")
    @Mapping(source = "projectId", target = "project", qualifiedByName = "projectFromId")
    @Mapping(source = "responsiblePersonId", target = "responsiblePerson", qualifiedByName = "employeeFromId")
    @Mapping(source = "projectHiringStatusId", target = "projectHiringStatus", qualifiedByName = "projectHiringStatusFromId")
    public abstract void updateEntityFromDTO(InternProjectHistoryRequestDto dto, @MappingTarget InternProjectHistory entity);

    @Named("internStaffingFromId")
    protected InternStaffing internStaffingFromId(Long id) {
        return id == null ? null : internStaffingRepository.findById(id).orElse(null);
    }

    @Named("projectFromId")
    protected Project projectFromId(Long id) {
        return id == null ? null : projectRepository.findById(id).orElse(null);
    }

    @Named("employeeFromId")
    protected Employee employeeFromId(Long id) {
        return id == null ? null : employeeRepository.findById(id).orElse(null);
    }

    @Named("projectHiringStatusFromId")
    protected ProjectHiringStatus projectHiringStatusFromId(Long id) {
        return id == null ? null : projectHiringStatusRepository.findById(id).orElse(null);
    }

}
