package com.accenture.ems.emstraining.service;

import com.accenture.ems.emstraining.entity.InternProjectHistory;
import com.accenture.ems.emstraining.mapper.InternProjectHistoryMapper;
import com.accenture.ems.emstraining.model.InternProjectHistoryRequestDto;
import com.accenture.ems.emstraining.model.InternProjectHistoryResponseDto;
import com.accenture.ems.emstraining.repository.InternProjectHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InternProjectHistoryServiceImpl implements InternProjectHistoryService{

    private final InternProjectHistoryRepository internProjectHistoryRepository;
    private final InternProjectHistoryMapper internProjectHistoryMapper;

    @Override
    public List<InternProjectHistoryResponseDto> getAll() {
        log.info("Finding all InternProjectHistories");
        List<InternProjectHistory> entities = internProjectHistoryRepository.findAll();
        return entities.stream().map(internProjectHistoryMapper::internProjectHistoryEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<InternProjectHistoryResponseDto> getById(Long id) {
        log.info("Finding InternProjectHistory with id: {}", id);
        return internProjectHistoryRepository.findById(id).map(internProjectHistoryMapper::internProjectHistoryEntityToDTO);
    }

    @Override
    public InternProjectHistoryResponseDto create(InternProjectHistoryRequestDto dto) {
        InternProjectHistory entity = internProjectHistoryMapper.internProjectHistoryDTOtoEntity(dto);
        InternProjectHistory saved = internProjectHistoryRepository.save(entity);
        log.info("Created InternProjectHistory with id: {}", saved.getId());

        return internProjectHistoryMapper.internProjectHistoryEntityToDTO(saved);
    }

    @Override
    public InternProjectHistoryResponseDto update(Long id, InternProjectHistoryRequestDto internProjectHistoryRequestDto) {
        log.info("Updating InternProjectHistory with id: {}", id);
        InternProjectHistory existingInternProjectHistory = internProjectHistoryRepository.findById(id).get();
        internProjectHistoryMapper.updateEntityFromDTO(internProjectHistoryRequestDto, existingInternProjectHistory);
        InternProjectHistory updatedInternProjectHistory = internProjectHistoryRepository.save(existingInternProjectHistory);
        log.info("Updated InternProjectHistory with id: {}", id);

        return internProjectHistoryMapper.internProjectHistoryEntityToDTO(updatedInternProjectHistory);
    }

    @Override
    public void delete(Long id) {
        internProjectHistoryRepository.deleteById(id);
        log.info("Deleted InternProjectHistory with id: {}", id);
    }
}
