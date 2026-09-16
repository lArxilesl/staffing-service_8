package com.accenture.ems.emstraining.service;

import com.accenture.ems.emstraining.model.InternStaffingResponse;
import com.accenture.ems.emstraining.model.InternStaffingSummaryResponse;

import java.util.List;
import java.util.Optional;

public interface InternStaffingService {
    List<InternStaffingSummaryResponse> findAll();

    Optional<InternStaffingResponse> findById(Long id);
}