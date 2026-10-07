package com.accenture.ems.emstraining.service;

import com.accenture.ems.emstraining.entity.Employee;
import com.accenture.ems.emstraining.entity.InternHiringStatusEntity;
import com.accenture.ems.emstraining.entity.InternStaffing;
import com.accenture.ems.emstraining.exception.ResourceNotFoundException;
import com.accenture.ems.emstraining.exception.StaffingHasProjectHistoryException;
import com.accenture.ems.emstraining.mapper.InternStaffingMapper;
import com.accenture.ems.emstraining.model.*;
import com.accenture.ems.emstraining.repository.EmployeeRepository;
import com.accenture.ems.emstraining.repository.InternHiringStatusRepository;
import com.accenture.ems.emstraining.repository.InternProjectHistoryRepository;
import com.accenture.ems.emstraining.repository.InternStaffingRepository;
import com.accenture.ems.emstraining.service.impl.InternStaffingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("InternStaffingService Tests")
@ExtendWith(MockitoExtension.class)
class InternStaffingServiceTest {
    @Mock
    private InternStaffingRepository internStaffingRepository;

    @Mock
    private InternProjectHistoryRepository internProjectHistoryRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private InternHiringStatusRepository internHiringStatusRepository;

    @Mock
    private InternStaffingMapper internStaffingMapper;

    @InjectMocks
    private InternStaffingServiceImpl internStaffingService;

    private InternStaffing internStaffing;
    private InternStaffingResponse internStaffingResponse;
    private InternHiringStatusResponse internHiringStatusResponse;
    private InternHiringStatusEntity internHiringStatusEntity;
    private EmployeeResponse employeeResponse;
    private Employee employee;
    private InternStaffingRequest internStaffingRequest;

    @BeforeEach
    void setUp() {
        internStaffing = new InternStaffing();
        internStaffing.setId(1L);
        internStaffing.setEmployeeId(1L);
        internStaffing.setInternHiringStatusId(2L);

        employee = new Employee();
        employee.setEmployeeId(1L);

        internHiringStatusEntity = new InternHiringStatusEntity();
        internHiringStatusEntity.setId(2L);

        employeeResponse = new EmployeeResponse();
        employeeResponse.setEmployeeId(1L);
        employeeResponse.setName("Name1");
        employeeResponse.setSurname("Surname1");
        employeeResponse.setStartDate("Start1");
        employeeResponse.setEndDate("End1");

        internHiringStatusResponse = new InternHiringStatusResponse();
        internHiringStatusResponse.setId(2L);
        internHiringStatusResponse.setStatus("Status2");

        internStaffingResponse = new InternStaffingResponse();
        internStaffingResponse.setEmployee(employeeResponse);
        internStaffingResponse.setId(1L);
        internStaffingResponse.setInternHiringStatus(internHiringStatusResponse);
        internStaffingResponse.setExtension(LocalDateTime.parse("2026-10-01T00:00:00"));
        internStaffingResponse.setInternshipWorkload(10);
        internStaffingResponse.setWorkload(1);

        internStaffingRequest = new InternStaffingRequest();
        internStaffingRequest.setEmployeeId(1L);
        internStaffingRequest.setInternHiringStatusId(2L);
        internStaffingRequest.setInternshipWorkload(10);
        internStaffingRequest.setWorkload(1);
        internStaffingRequest.setExtension(LocalDateTime.parse("2026-10-01T00:00:00"));
    }

    @Test
    @DisplayName("getById Should return staffing response when ID exists")
    void getByIdShouldReturnStaffingWhenIdExists() {
        when(internStaffingRepository.findById(1L)).thenReturn(Optional.of(internStaffing));
        when(internStaffingMapper.toResponse(any(), any(), any())).thenReturn(internStaffingResponse);

        Optional<InternStaffingResponse> result = internStaffingService.getById(1L);

        assertThat(result)
                .isPresent()
                .contains(internStaffingResponse);

        verify(internStaffingRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getAll should return empty list when no staffings exist")
    void getAllShouldReturnEmptyListWhenNoStaffingsExist() {
        when(internStaffingRepository.findAll()).thenReturn(Collections.emptyList());
        when(internStaffingMapper.toResponseList(Collections.emptyList())).thenReturn(Collections.emptyList());

        assertThat(internStaffingService.getAll()).isEmpty();

        verify(internStaffingMapper, times(1)).toResponseList(Collections.emptyList());
    }

    @Test
    @DisplayName("getAll should return a list of all staffings")
    void getAllShouldReturnAllStaffings() {
        InternStaffing internStaffing2 = new InternStaffing();
        internStaffing2.setId(2L);
        InternStaffingSummaryResponse internStaffingSummaryResponse = new InternStaffingSummaryResponse();
        internStaffingSummaryResponse.setId(1L);
        InternStaffingSummaryResponse internStaffingSummaryResponse2 = new InternStaffingSummaryResponse();
        internStaffingSummaryResponse2.setId(2L);

        List<InternStaffing> internStaffings = Arrays.asList(internStaffing, internStaffing2);
        when(internStaffingRepository.findAll()).thenReturn(internStaffings);

        List<InternStaffingSummaryResponse> internStaffingsSummaryResponses = Arrays.asList(
                internStaffingSummaryResponse, internStaffingSummaryResponse2);
        when(internStaffingMapper.toResponseList(internStaffings)).thenReturn(internStaffingsSummaryResponses);

        List<InternStaffingSummaryResponse> result = internStaffingService.getAll();
        assertThat(result)
                .containsAll(internStaffingsSummaryResponses)
                .hasSize(2);

        verify(internStaffingMapper, times(1)).toResponseList(anyList());
    }

    @Test
    @DisplayName("delete should delete entity if no history")
    void deleteShouldDeleteEntityIfNoHistory() {
        when(internProjectHistoryRepository.existsByInternStaffingId(1L)).thenReturn(false);

        internStaffingService.delete(1L);

        verify(internStaffingRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("delete should throw StaffingHasProjectHistoryException if staffing has history")
    void deleteShouldThrowStaffingHasProjectHistoryExceptionIfStaffingHasHistory() {
        when(internProjectHistoryRepository.existsByInternStaffingId(1L)).thenReturn(true);

        assertThatExceptionOfType(StaffingHasProjectHistoryException.class)
                .isThrownBy(() -> internStaffingService.delete(1L))
                .withMessage("Cannot delete intern staffing with id: 1 because it has project history");

        verify(internStaffingRepository, never()).deleteById(1L);
    }

    @Test
    @DisplayName("create should save and return response when valid request")
    void createShouldSaveAndReturnResponseWhenValidRequest() {
        when(internStaffingRepository.save(any(InternStaffing.class))).thenReturn(internStaffing);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(internHiringStatusRepository.findById(2L)).thenReturn(Optional.of(internHiringStatusEntity));
        when(internStaffingMapper.toEntity(any(InternStaffingRequest.class))).thenReturn(internStaffing);
        when(internStaffingMapper.toResponse(any(), any(), any())).thenReturn(internStaffingResponse);

        InternStaffingResponse response = internStaffingService.create(internStaffingRequest);

        assertThat(response).isEqualTo(internStaffingResponse);
        assertThat(internStaffing.getEmployeeId()).isEqualTo(employee.getEmployeeId());
        assertThat(internStaffing.getInternHiringStatusId()).isEqualTo(internHiringStatusEntity.getId());

        verify(internStaffingRepository, times(1)).save(internStaffing);
    }

    @Test
    @DisplayName("create should throw ResourceNotFoundException when employee does not exist")
    void createShouldThrowResourceNotFoundExceptionWhenEmployeeDoesNotExist() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> internStaffingService.create(internStaffingRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee with id 1 not found");

        verify(internHiringStatusRepository, never()).findById(any());
        verify(internStaffingRepository, never()).save(any());
    }

    @Test
    @DisplayName("create should throw ResourceNotFoundException when hiring status does not exist")
    void createShouldThrowResourceNotFoundExceptionWhenInternHiringStatusDoesNotExist() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(internHiringStatusRepository.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> internStaffingService.create(internStaffingRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("InternHiringStatus with id 2 not found");

        verify(internStaffingRepository, never()).save(any());
    }

    @Test
    @DisplayName("update should throw ResourceNotFoundException when staffing does not exist")
    void updateShouldThrowResourceNotFoundExceptionWhenStaffingDoesNotExist() {
        InternStaffingPatchRequest patchRequest = new InternStaffingPatchRequest();

        when(internStaffingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> internStaffingService.update(1L, patchRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Intern Staffing with id: 1 not found");

        verify(internStaffingRepository, never()).save(any());
        verify(internHiringStatusRepository, never()).findById(any());
        verify(employeeRepository, never()).findById(any());
    }

    @Test
    @DisplayName("update should update all fields when existing employee and status ID provided")
    void updateShouldUpdateAllFieldsWhenExistingEmployeeAndStatusIDProvided() {
        InternStaffingPatchRequest patchRequest = new InternStaffingPatchRequest();
        patchRequest.setEmployeeId(1L);
        patchRequest.setInternHiringStatusId(2L);
        patchRequest.setWorkload(1);
        patchRequest.setInternshipWorkload(10);
        patchRequest.setExtension(LocalDateTime.parse("2026-10-01T00:00:00"));

        when(internStaffingRepository.findById(1L)).thenReturn(Optional.of(internStaffing));
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(internHiringStatusRepository.findById(2L)).thenReturn(Optional.of(internHiringStatusEntity));
        when(internStaffingRepository.save(internStaffing)).thenReturn(internStaffing);
        when(internStaffingMapper.toResponse(any(), any(), any())).thenReturn(internStaffingResponse);

        InternStaffingResponse response = internStaffingService.update(1L, patchRequest);

        assertThat(response).isEqualTo(internStaffingResponse);
        assertThat(response.getEmployee()).isEqualTo(employeeResponse);
        assertThat(response.getInternHiringStatus()).isEqualTo(internHiringStatusResponse);
        assertThat(response.getWorkload()).isEqualTo(1);
        assertThat(response.getInternshipWorkload()).isEqualTo(10);
        assertThat(response.getExtension()).isEqualTo("2026-10-01T00:00:00");

        verify(internStaffingMapper, times(1)).updateStaffing(patchRequest, internStaffing);
        verify(internStaffingRepository, times(1)).save(internStaffing);

    }

    @Test
    @DisplayName("update should not fetch employee or status when their ID are null in patch request")
    void updateShouldNotFetchEmployeeOrStatusWhenTheirIDAreNullInPatchRequest() {
        InternStaffingPatchRequest patchRequest = new InternStaffingPatchRequest();
        patchRequest.setWorkload(42);

        when(internStaffingRepository.findById(1L)).thenReturn(Optional.of(internStaffing));
        when(internStaffingRepository.save(internStaffing)).thenReturn(internStaffing);
        when(internStaffingMapper.toResponse(any(), any(), any())).thenReturn(internStaffingResponse);

        InternStaffingResponse response = internStaffingService.update(1L, patchRequest);

        assertThat(response).isEqualTo(internStaffingResponse);

        verify(internStaffingMapper, times(1)).updateStaffing(patchRequest, internStaffing);
        verify(employeeRepository, never()).findById(any());
        verify(internHiringStatusRepository, never()).findById(any());
        verify(internStaffingRepository, times(1)).save(internStaffing);
    }

    @Test
    @DisplayName("update should throw ResourceNotFoundException when updated employee does not exist")
    void updateShouldThrowResourceNotFoundExceptionWhenUpdatedEmployeeDoesNotExist() {
        InternStaffingPatchRequest patchRequest = new InternStaffingPatchRequest();
        patchRequest.setEmployeeId(42L);

        when(internStaffingRepository.findById(1L)).thenReturn(Optional.of(internStaffing));
        when(employeeRepository.findById(42L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> internStaffingService.update(1L, patchRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee with id 42 not found");

        verify(internStaffingRepository, never()).save(any());
    }

    @Test
    @DisplayName("update should throw ResourceNotFoundException when updated hiring status does not exist")
    void updateShouldThrowResourceNotFoundExceptionWhenUpdatedInternHiringStatusDoesNotExist() {
        InternStaffingPatchRequest patchRequest = new InternStaffingPatchRequest();
        patchRequest.setInternHiringStatusId(42L);

        when(internStaffingRepository.findById(1L)).thenReturn(Optional.of(internStaffing));
        when(internHiringStatusRepository.findById(42L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> internStaffingService.update(1L, patchRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No Hiring Status with id 42 found");

        verify(internStaffingRepository, never()).save(any());
    }

    @Test
    @DisplayName("existsById should return true if staffing exists")
    void existsByIdShouldReturnTrueIfStaffingExists() {
        when(internStaffingRepository.existsById(1L)).thenReturn(true);

        assertThat(internStaffingService.existsById(1L)).isTrue();

        verify(internStaffingRepository, times(1)).existsById(1L);
    }

    @Test
    @DisplayName("existsById should return false if staffing does not exist")
    void existsByIdShouldReturnFalseIfStaffingDoesNotExist() {
        when(internStaffingRepository.existsById(1L)).thenReturn(false);

        assertThat(internStaffingService.existsById(1L)).isFalse();

        verify(internStaffingRepository, times(1)).existsById(1L);
    }
}
