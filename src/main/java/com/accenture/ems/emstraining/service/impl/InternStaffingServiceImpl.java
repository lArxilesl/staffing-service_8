package com.accenture.ems.emstraining.service.impl;

import com.accenture.ems.emstraining.entity.InternStaffing;
import com.accenture.ems.emstraining.exception.ResourceNotFoundException;
import com.accenture.ems.emstraining.exception.StaffingHasProjectHistoryException;
import com.accenture.ems.emstraining.mapper.InternStaffingMapper;
import com.accenture.ems.emstraining.model.InternStaffingRequest;
import com.accenture.ems.emstraining.model.InternStaffingResponse;
import com.accenture.ems.emstraining.model.InternStaffingSummaryResponse;
import com.accenture.ems.emstraining.repository.EmployeeRepository;
import com.accenture.ems.emstraining.repository.InternHiringStatusRepository;
import com.accenture.ems.emstraining.repository.InternProjectHistoryRepository;
import com.accenture.ems.emstraining.repository.InternStaffingRepository;
import com.accenture.ems.emstraining.service.InternStaffingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InternStaffingServiceImpl implements InternStaffingService {
    private final InternStaffingRepository internStaffingRepository;
    private final InternStaffingMapper internStaffingMapper;
    private final InternProjectHistoryRepository internProjectHistoryRepository;
    private final EmployeeRepository employeeRepository;
    private final InternHiringStatusRepository internHiringStatusRepository;

    @Override
    public List<InternStaffingSummaryResponse> findAll() {
        log.debug("Fetching all intern staffings");
        return internStaffingMapper.toResponseList(internStaffingRepository.findAll());
    }

    @Override
    public InternStaffingResponse findById(Long id) {
        log.info("Request to find intern staffing {}", id);
        return internStaffingRepository
                .findById(id)
                .map(internStaffingMapper::toResponse)
                .orElseThrow(() ->
                {
                    log.warn("No Intern Staffing with id {} found", id);
                    return new ResourceNotFoundException(String.format("Intern Staffing with id: %d not found", id));
                });
    }

    @Override
    public void delete(Long id) {
        log.info("Request to delete intern staffing {}", id);
        if (!internStaffingRepository.existsById(id)) {
            log.warn("Intern Staffing with id {} does not exist", id);
            throw new ResourceNotFoundException(String.format("Intern Staffing with id: %d not found", id));
        }
        if (internProjectHistoryRepository.existsByInternStaffingId(id)) {
            log.warn("Cannot delete intern staffing with id: {} because it has project history", id);
            throw new StaffingHasProjectHistoryException(String.format("Cannot delete intern staffing with id: %d because it has project history", id));
        }
        log.info("Intern staffing with id {} deleted", id);
        internStaffingRepository.deleteById(id);
    }

    @Override
    public InternStaffingResponse create(InternStaffingRequest internStaffingRequest) {
        log.info("Request to create staffing");
        if (!employeeRepository.existsById(internStaffingRequest.getEmployeeId())) {
            log.warn("No Employee with id {} found", internStaffingRequest.getEmployeeId());
            throw new ResourceNotFoundException(String.format("No Employee with id %d found", internStaffingRequest.getEmployeeId()));
        }
        if (!internHiringStatusRepository.existsById(internStaffingRequest.getInternHiringStatusId())) {
            log.warn("No Hiring Status with id {} found", internStaffingRequest.getInternHiringStatusId());
            throw new ResourceNotFoundException(String.format("No Hiring Status with id %d found", internStaffingRequest.getInternHiringStatusId()));
        }
        InternStaffing entity = internStaffingRepository.save(internStaffingMapper.toEntity(internStaffingRequest));

        return internStaffingMapper.toResponse(entity);
    }
}
