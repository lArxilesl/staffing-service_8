package com.accenture.ems.emstraining.mapper;

import com.accenture.ems.emstraining.entity.InternHiringStatusEntity;
import com.accenture.ems.emstraining.model.InternHiringStatusRequest;
import com.accenture.ems.emstraining.model.InternHiringStatusResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InternHiringStatusMapper {

    InternHiringStatusEntity toEntity(InternHiringStatusRequest request);

    InternHiringStatusResponse toResponse(InternHiringStatusEntity entity);

    List<InternHiringStatusResponse> toResponseList(List<InternHiringStatusEntity> entities);

    void updateEntityFromRequest(InternHiringStatusRequest request, @MappingTarget InternHiringStatusEntity entity);
}
