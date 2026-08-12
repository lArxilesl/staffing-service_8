package com.accenture.ems.emstraining.repository;

import com.accenture.ems.emstraining.entity.InternStaffing;
import com.accenture.ems.emstraining.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {}
