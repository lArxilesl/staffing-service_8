package com.accenture.ems.emstraining.repository;

import com.accenture.ems.emstraining.entity.ProjectHiringStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectHiringStatusRepository
        extends JpaRepository<ProjectHiringStatusEntity, Long> {
}