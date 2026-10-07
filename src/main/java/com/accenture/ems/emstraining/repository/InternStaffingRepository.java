package com.accenture.ems.emstraining.repository;

import com.accenture.ems.emstraining.entity.InternStaffing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InternStaffingRepository extends JpaRepository<InternStaffing, Long> {
    @Override
//    @EntityGraph(attributePaths = {"employee", "internHiringStatus"})
    List<InternStaffing> findAll();

    @Override
//    @EntityGraph(attributePaths = {"employee", "internHiringStatus"})
    InternStaffing getOne(Long id);
}

