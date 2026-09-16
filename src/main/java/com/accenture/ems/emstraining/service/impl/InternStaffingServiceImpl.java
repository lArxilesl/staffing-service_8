package com.accenture.ems.emstraining.service.impl;

import com.accenture.ems.emstraining.entity.InternStaffing;
import com.accenture.ems.emstraining.mapper.InternStaffingMapper;
import com.accenture.ems.emstraining.model.InternStaffingResponse;
import com.accenture.ems.emstraining.model.InternStaffingSummaryResponse;
import com.accenture.ems.emstraining.repository.InternStaffingRepository;
import com.accenture.ems.emstraining.service.InternStaffingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InternStaffingServiceImpl implements InternStaffingService {
    private final InternStaffingRepository internStaffingRepository;
    private final InternStaffingMapper internStaffingMapper;

    @Override
    public List<InternStaffingSummaryResponse> findAll() {
        log.debug("Fetching all intern staffings");
        List<InternStaffing> staffings = internStaffingRepository.findAll();
        return internStaffingMapper.toResponseList(staffings);
    }

    @Override
    public Optional<InternStaffingResponse> findById(Long id) {
        return internStaffingRepository.findById(id).map(internStaffingMapper::toResponse);
    }
}
