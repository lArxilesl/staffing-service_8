package com.accenture.ems.emstraining.service;

import com.accenture.ems.emstraining.model.InternProjectHistoryRequestDto;
import com.accenture.ems.emstraining.model.InternProjectHistoryResponseDto;

import java.util.List;
import java.util.Optional;

public interface InternProjectHistoryService {
    List<InternProjectHistoryResponseDto> getAll();

    Optional<InternProjectHistoryResponseDto> getById(Long id);

    InternProjectHistoryResponseDto create(InternProjectHistoryRequestDto dto);

    InternProjectHistoryResponseDto update(Long id, InternProjectHistoryRequestDto internProjectHistoryRequestDto);

    void delete(Long id);
}
