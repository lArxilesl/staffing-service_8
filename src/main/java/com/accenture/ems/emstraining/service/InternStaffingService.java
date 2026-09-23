package com.accenture.ems.emstraining.service;

import com.accenture.ems.emstraining.model.InternStaffingResponse;
import com.accenture.ems.emstraining.model.InternStaffingSummaryResponse;

import java.util.List;

public interface InternStaffingService {
    List<InternStaffingSummaryResponse> findAll();

    InternStaffingResponse findById(Long id);

    void delete(Long id);
}