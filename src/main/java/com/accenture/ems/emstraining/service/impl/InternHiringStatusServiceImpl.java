package com.accenture.ems.emstraining.service.impl;

import com.accenture.ems.emstraining.entity.InternHiringStatusEntity;
import com.accenture.ems.emstraining.exception.ResourceNotFoundException;
import com.accenture.ems.emstraining.mapper.InternHiringStatusMapper;
import com.accenture.ems.emstraining.model.InternHiringStatusRequest;
import com.accenture.ems.emstraining.model.InternHiringStatusResponse;
import com.accenture.ems.emstraining.repository.InternHiringStatusRepository;
import com.accenture.ems.emstraining.service.InternHiringStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InternHiringStatusServiceImpl implements InternHiringStatusService {

    private final InternHiringStatusRepository internHiringStatusRepository;
    private final InternHiringStatusMapper internHiringStatusMapper;

    @Override
    public InternHiringStatusResponse getById(Long id) {
        InternHiringStatusEntity entity = findEntityById(id);
        return internHiringStatusMapper.toResponse(entity);
    }

    @Override
    public List<InternHiringStatusResponse> getAll() {
        List<InternHiringStatusEntity> entities = internHiringStatusRepository.findAll();
        return internHiringStatusMapper.toResponseList(entities);
    }

    @Override
    public InternHiringStatusResponse create(InternHiringStatusRequest request) {
        InternHiringStatusEntity entity = internHiringStatusMapper.toEntity(request);
        InternHiringStatusEntity savedEntity = internHiringStatusRepository.save(entity);
        return internHiringStatusMapper.toResponse(savedEntity);
    }

    @Override
    public InternHiringStatusResponse update(Long id, InternHiringStatusRequest request) {
        InternHiringStatusEntity entity = findEntityById(id);
        internHiringStatusMapper.updateEntityFromRequest(request, entity);
        InternHiringStatusEntity updatedEntity = internHiringStatusRepository.save(entity);
        return internHiringStatusMapper.toResponse(updatedEntity);
    }

    @Override
    public void delete(Long id) {
        InternHiringStatusEntity entity = findEntityById(id);
        internHiringStatusRepository.delete(entity);
    }

    private InternHiringStatusEntity findEntityById(Long id) {
        return internHiringStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Intern Hiring Status not found with id: " + id));
    }
}
