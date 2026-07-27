package com.accenture.ems.emstraining.mapper;

import com.accenture.ems.emstraining.entity.InternProjectHistory;
import com.accenture.ems.emstraining.model.InternProjectHistoryRequestDto;
import com.accenture.ems.emstraining.model.InternProjectHistoryResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InternProjectHistoryMapper {
    InternProjectHistoryResponseDto internProjectHistoryEntityToDTO(InternProjectHistory entity);

    InternProjectHistory internProjectHistoryDTOtoEntity(InternProjectHistoryRequestDto dto);

    void updateEntityFromDTO(InternProjectHistoryRequestDto dto, @MappingTarget InternProjectHistory entity);
}
