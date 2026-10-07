package com.accenture.ems.emstraining.service;

import com.accenture.ems.emstraining.model.InternStaffingPatchRequest;
import com.accenture.ems.emstraining.model.InternStaffingRequest;
import com.accenture.ems.emstraining.model.InternStaffingResponse;
import com.accenture.ems.emstraining.model.InternStaffingSummaryResponse;

import java.util.List;
import java.util.Optional;

public interface InternStaffingService {
    List<InternStaffingSummaryResponse> getAll();

    Optional<InternStaffingResponse> getById(Long id);

    boolean existsById(Long id);

    void delete(Long id);

    InternStaffingResponse create(InternStaffingRequest internStaffingRequest);

    InternStaffingResponse update(Long id, InternStaffingPatchRequest internStaffingRequest);
}