package com.accenture.ems.emstraining.service.impl;

import com.accenture.ems.emstraining.entity.InternHiringStatusEntity;
import com.accenture.ems.emstraining.mapper.InternHiringStatusMapper;
import com.accenture.ems.emstraining.model.InternHiringStatusRequest;
import com.accenture.ems.emstraining.model.InternHiringStatusResponse;
import com.accenture.ems.emstraining.repository.InternHiringStatusRepository;
import com.accenture.ems.emstraining.service.InternHiringStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InternHiringStatusServiceImpl implements InternHiringStatusService {

    private final InternHiringStatusRepository internHiringStatusRepository;
    private final InternHiringStatusMapper internHiringStatusMapper;

    @Override
    public Optional<InternHiringStatusResponse> getById(Long id) {
        log.debug("Fetching intern hiring status with id: {}", id);

        return internHiringStatusRepository.findById(id)
                .map(internHiringStatusMapper::toResponse);
    }

    @Override
    public List<InternHiringStatusResponse> getAll() {
        log.debug("Fetching all intern hiring statuses");
        List<InternHiringStatusEntity> entities = internHiringStatusRepository.findAll();
        return internHiringStatusMapper.toResponseList(entities);
    }

    @Override
    public void create(InternHiringStatusRequest request) {
        log.debug("Creating intern hiring status");
        InternHiringStatusEntity entity = internHiringStatusMapper.toEntity(request);
        internHiringStatusRepository.save(entity);

        log.info("Intern hiring status created successfully");
    }

    @Override
    public boolean update(Long id, InternHiringStatusRequest request) {
        log.debug("Updating intern hiring status with id: {}", id);

        Optional<InternHiringStatusEntity> entityOptional =
                internHiringStatusRepository.findById(id);

        if (entityOptional.isPresent()) {
            InternHiringStatusEntity entity = entityOptional.get();
            internHiringStatusMapper.updateEntityFromRequest(request, entity);
            internHiringStatusRepository.save(entity);
            log.info("Intern hiring status with id {} updated successfully", id);
            return true;
        } else {
            log.warn("Intern Hiring Status not found with id: {}", id);
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        log.debug("Deleting intern hiring status with id: {}", id);

        Optional<InternHiringStatusEntity> entityOptional =
                internHiringStatusRepository.findById(id);

        if (entityOptional.isPresent()) {
            internHiringStatusRepository.delete(entityOptional.get());
            log.info("Intern hiring status with id {} deleted successfully", id);
            return true;
        } else {
            log.warn("Intern Hiring Status not found with id: {}", id);
            return false;
        }
    }
}
