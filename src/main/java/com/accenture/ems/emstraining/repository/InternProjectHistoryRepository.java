package com.accenture.ems.emstraining.repository;

import com.accenture.ems.emstraining.entity.InternProjectHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InternProjectHistoryRepository extends JpaRepository<InternProjectHistory, Long> {
    boolean existsByInternStaffingId(Long internStaffingId);
}
