package com.accenture.ems.emstraining.service;

import com.accenture.ems.emstraining.model.InternHiringStatusRequest;
import com.accenture.ems.emstraining.model.InternHiringStatusResponse;

import java.util.List;
import java.util.Optional;

public interface InternHiringStatusService {

    Optional<InternHiringStatusResponse> getById(Long id);

    List<InternHiringStatusResponse> getAll();

    void create(InternHiringStatusRequest request);

    boolean update(Long id, InternHiringStatusRequest request);

    boolean delete(Long id);
}

