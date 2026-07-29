package com.accenture.ems.emstraining.service;

import com.accenture.ems.emstraining.model.ProjectHiringStatusRequest;
import com.accenture.ems.emstraining.model.ProjectHiringStatusResponse;

import java.util.List;
import java.util.Optional;

public interface ProjectHiringStatusService {

    Optional<ProjectHiringStatusResponse> getById(Long id);

    List<ProjectHiringStatusResponse> getAll();

    void create(ProjectHiringStatusRequest request);

    boolean update(Long id, ProjectHiringStatusRequest request);

    boolean delete(Long id);
}