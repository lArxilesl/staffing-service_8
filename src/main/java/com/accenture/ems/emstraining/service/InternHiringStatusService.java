package com.accenture.ems.emstraining.service;

import com.accenture.ems.emstraining.model.InternHiringStatusRequest;
import com.accenture.ems.emstraining.model.InternHiringStatusResponse;

import java.util.List;

public interface InternHiringStatusService {

    InternHiringStatusResponse getById(Long id);

    List<InternHiringStatusResponse> getAll();

    void create(InternHiringStatusRequest request);

    void update(Long id, InternHiringStatusRequest request);

    void delete(Long id);
}

