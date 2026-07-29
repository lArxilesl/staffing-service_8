package com.accenture.ems.emstraining.mapper;

import com.accenture.ems.emstraining.entity.ProjectHiringStatusEntity;
import com.accenture.ems.emstraining.model.ProjectHiringStatusRequest;
import com.accenture.ems.emstraining.model.ProjectHiringStatusResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectHiringStatusMapper {

    ProjectHiringStatusEntity toEntity(ProjectHiringStatusRequest request);

    ProjectHiringStatusResponse toResponse(ProjectHiringStatusEntity entity);

    List<ProjectHiringStatusResponse> toResponseList(List<ProjectHiringStatusEntity> entities);

    void updateEntityFromRequest(
            ProjectHiringStatusRequest request,
            @MappingTarget ProjectHiringStatusEntity entity
    );
}