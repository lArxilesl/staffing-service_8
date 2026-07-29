package com.accenture.ems.emstraining.service.impl;

import com.accenture.ems.emstraining.entity.ProjectHiringStatusEntity;
import com.accenture.ems.emstraining.mapper.ProjectHiringStatusMapper;
import com.accenture.ems.emstraining.model.ProjectHiringStatusRequest;
import com.accenture.ems.emstraining.model.ProjectHiringStatusResponse;
import com.accenture.ems.emstraining.repository.ProjectHiringStatusRepository;
import com.accenture.ems.emstraining.service.ProjectHiringStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectHiringStatusServiceImpl implements ProjectHiringStatusService {

    private final ProjectHiringStatusRepository projectHiringStatusRepository;
    private final ProjectHiringStatusMapper projectHiringStatusMapper;

    @Override
    public Optional<ProjectHiringStatusResponse> getById(Long id) {
        log.debug("Fetching project hiring status with id: {}", id);

        return projectHiringStatusRepository.findById(id)
                .map(projectHiringStatusMapper::toResponse);
    }

    @Override
    public List<ProjectHiringStatusResponse> getAll() {
        log.debug("Fetching all project hiring statuses");
        List<ProjectHiringStatusEntity> entities = projectHiringStatusRepository.findAll();
        return projectHiringStatusMapper.toResponseList(entities);
    }

    @Override
    public void create(ProjectHiringStatusRequest request) {
        log.debug("Creating project hiring status");
        ProjectHiringStatusEntity entity = projectHiringStatusMapper.toEntity(request);
        projectHiringStatusRepository.save(entity);

        log.info("Project hiring status created successfully");
    }

    @Override
    public boolean update(Long id, ProjectHiringStatusRequest request) {
        log.debug("Updating project hiring status with id: {}", id);

        Optional<ProjectHiringStatusEntity> entityOptional =
                projectHiringStatusRepository.findById(id);

        if (entityOptional.isPresent()) {
            ProjectHiringStatusEntity entity = entityOptional.get();
            projectHiringStatusMapper.updateEntityFromRequest(request, entity);
            projectHiringStatusRepository.save(entity);
            log.info("Project hiring status with id {} updated successfully", id);
            return true;
        } else {
            log.warn("Project Hiring Status not found with id: {}", id);
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        log.debug("Deleting project hiring status with id: {}", id);

        Optional<ProjectHiringStatusEntity> entityOptional =
                projectHiringStatusRepository.findById(id);

        if (entityOptional.isPresent()) {
            projectHiringStatusRepository.delete(entityOptional.get());
            log.info("Project hiring status with id {} deleted successfully", id);
            return true;
        } else {
            log.warn("Project Hiring Status not found with id: {}", id);
            return false;
        }
    }
}