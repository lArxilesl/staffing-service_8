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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    @Transactional(readOnly = true)
    public List<InternStaffingSummaryResponse> getAll() {
        log.info("Fetching all intern staffings");
        return internStaffingMapper.toResponseList(internStaffingRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InternStaffingResponse> getById(Long id) {
        log.info("Request to find intern staffing {}", id);
        return internStaffingRepository
                .findById(id)
                .map(staffing -> internStaffingMapper.toResponse(
                        staffing,
                        findEmployeeById(staffing.getEmployeeId()),
                        findInternHiringStatusEntityById(staffing.getInternHiringStatusId())));
    }

    @Override
    public boolean existsById(Long id) {
        return internStaffingRepository.existsById(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Request to delete intern staffing {}", id);

        if (internProjectHistoryRepository.existsByInternStaffingId(id)) {
            log.warn("Cannot delete intern staffing with id: {} because it has project history", id);
            throw new StaffingHasProjectHistoryException(String.format("Cannot delete intern staffing with id: %d because it has project history", id));
        }
        internStaffingRepository.deleteById(id);
        log.info("Intern staffing with id: {} deleted", id);
    }

    @Override
    @Transactional
    public InternStaffingResponse create(InternStaffingRequest internStaffingRequest) {
        log.info("Request to create Intern Staffing : {}", internStaffingRequest);

        Employee employee = findEmployeeById(internStaffingRequest.getEmployeeId());
        InternHiringStatusEntity status = findInternHiringStatusEntityById(internStaffingRequest.getInternHiringStatusId());

        InternStaffing entity = internStaffingMapper.toEntity(internStaffingRequest);
        entity.setEmployeeId(employee.getEmployeeId());
        entity.setInternHiringStatusId(status.getId());

        return internStaffingMapper.toResponse(internStaffingRepository.save(entity), employee, status);
    }

    @Override
    @Transactional
    public InternStaffingResponse update(Long id, InternStaffingPatchRequest internStaffingPatchRequest) {
        log.info("Request to update Intern Staffing {} with: {}", id, internStaffingPatchRequest);

        log.info("Checking if Staffing {} exists", id);
        InternStaffing staffing = internStaffingRepository.findById(id).orElseThrow(() -> {
            log.warn("Intern Staffing with id: {} not found", id);
            return new ResourceNotFoundException(String.format("Intern Staffing with id: %d not found", id));
        });

        Employee employee = findEmployeeById((internStaffingPatchRequest.getEmployeeId()) != null
                ? internStaffingPatchRequest.getEmployeeId()
                : staffing.getEmployeeId());
        staffing.setEmployeeId(employee.getEmployeeId());

        InternHiringStatusEntity status = findInternHiringStatusEntityById((internStaffingPatchRequest.getInternHiringStatusId()) != null
                ? internStaffingPatchRequest.getInternHiringStatusId()
                : staffing.getInternHiringStatusId());
        staffing.setInternHiringStatusId(status.getId());

        internStaffingMapper.updateStaffing(internStaffingPatchRequest, staffing);

        return internStaffingMapper.toResponse(internStaffingRepository.save(staffing), employee, status);
    }

    private Employee findEmployeeById(Long employeeId) {
        log.info("Checking if Employee with id {} exists", employeeId);
        return employeeRepository
                .findById(employeeId)
                .orElseThrow(() -> {
                    log.warn("No Employee with id {} found", employeeId);
                    return new ResourceNotFoundException(String.format("Employee with id %d not found", employeeId));
                });
    }

    private InternHiringStatusEntity findInternHiringStatusEntityById(Long statusId) {
        log.info("Checking if Intern Hiring Status {} exists", statusId);
        return internHiringStatusRepository
                .findById(statusId)
                .orElseThrow(() -> {
                    log.warn("Intern Hiring Status with id {} not found", statusId);
                    return new ResourceNotFoundException(String.format("Intern Hiring Status with id %d not found", statusId));
                });
    }
}
