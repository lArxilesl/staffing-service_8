package com.accenture.ems.emstraining.service.impl;

import com.accenture.ems.emstraining.entity.Employee;
import com.accenture.ems.emstraining.entity.InternHiringStatusEntity;
import com.accenture.ems.emstraining.entity.InternStaffing;
import com.accenture.ems.emstraining.exception.ResourceNotFoundException;
import com.accenture.ems.emstraining.exception.StaffingHasProjectHistoryException;
import com.accenture.ems.emstraining.mapper.InternStaffingMapper;
import com.accenture.ems.emstraining.model.InternStaffingPatchRequest;
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

import javax.validation.Valid;
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
        log.info("Request to create Intern Staffing : {}", internStaffingRequest);

        log.info("Checking if Employee {} exists", internStaffingRequest.getEmployeeId());
        Employee employee = employeeRepository.findById(internStaffingRequest.getEmployeeId()).orElseThrow(() -> {
            log.warn("No Employee with id {} found", internStaffingRequest.getEmployeeId());
            return new ResourceNotFoundException(String.format("No Employee with id %d found", internStaffingRequest.getEmployeeId()));
        });
        log.info("Checking if InternHiringStatus {} exists", internStaffingRequest.getInternHiringStatusId());
        InternHiringStatusEntity status = internHiringStatusRepository.findById(internStaffingRequest.getInternHiringStatusId()).orElseThrow(() -> {
            log.warn("No Hiring Status with id {} found", internStaffingRequest.getInternHiringStatusId());
            return new ResourceNotFoundException(String.format("No Hiring Status with id %d found", internStaffingRequest.getInternHiringStatusId()));
        });

        InternStaffing entity = internStaffingMapper.toEntity(internStaffingRequest);
        entity.setEmployee(employee);
        entity.setInternHiringStatus(status);

        return internStaffingMapper.toResponse(internStaffingRepository.save(entity));
    }

    @Override
    public InternStaffingResponse update(Long id, @Valid InternStaffingPatchRequest internStaffingPatchRequest) {
        log.info("Request to update Intern Staffing {} with: {}", id, internStaffingPatchRequest);

        log.info("Checking if Staffing {} exists", id);
        InternStaffing staffing = internStaffingRepository.findById(id).orElseThrow(() -> {
            log.warn("No Staffing with id {} found", id);
            return new ResourceNotFoundException(String.format("No Staffing with id %d found", id));
        });

        if (internStaffingPatchRequest.getEmployeeId() != null) {
            log.info("Checking if Employee {} exists", internStaffingPatchRequest.getEmployeeId());
            Employee employee = employeeRepository.findById(internStaffingPatchRequest.getEmployeeId()).orElseThrow(() -> {
                log.warn("No Employee with id {} found", internStaffingPatchRequest.getEmployeeId());
                return new ResourceNotFoundException(String.format("No Employee with id %d found", internStaffingPatchRequest.getEmployeeId()));
            });
            staffing.setEmployee(employee);
        }

        if (internStaffingPatchRequest.getInternHiringStatusId() != null) {
            log.info("Checking if InternHiringStatus {} exists", internStaffingPatchRequest.getInternHiringStatusId());
            InternHiringStatusEntity status = internHiringStatusRepository.findById(internStaffingPatchRequest.getInternHiringStatusId()).orElseThrow(() -> {
                log.warn("No Hiring Status with id {} found", internStaffingPatchRequest.getInternHiringStatusId());
                return new ResourceNotFoundException(String.format("No Hiring Status with id %d found", internStaffingPatchRequest.getInternHiringStatusId()));
            });
            staffing.setInternHiringStatus(status);
        }

        internStaffingMapper.updateStaffing(internStaffingPatchRequest, staffing);


        return internStaffingMapper.toResponse(internStaffingRepository.save(staffing));
    }
}
