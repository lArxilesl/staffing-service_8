package com.accenture.ems.emstraining.service;

import com.accenture.ems.emstraining.model.InternStaffingPatchRequest;
import com.accenture.ems.emstraining.model.InternStaffingRequest;
import com.accenture.ems.emstraining.model.InternStaffingResponse;
import com.accenture.ems.emstraining.model.InternStaffingSummaryResponse;

import javax.validation.Valid;
import java.util.List;

public interface InternStaffingService {
    List<InternStaffingSummaryResponse> getAll();

    InternStaffingResponse getById(Long id);

    void delete(Long id);

    InternStaffingResponse create(@Valid InternStaffingRequest internStaffingRequest);

    InternStaffingResponse update(Long id, @Valid InternStaffingPatchRequest internStaffingRequest);
}