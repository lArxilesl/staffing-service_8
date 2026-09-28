package com.accenture.ems.emstraining.service;

import com.accenture.ems.emstraining.entity.InternStaffing;
import com.accenture.ems.emstraining.exception.ResourceNotFoundException;
import com.accenture.ems.emstraining.exception.StaffingHasProjectHistoryException;
import com.accenture.ems.emstraining.mapper.InternStaffingMapper;
import com.accenture.ems.emstraining.model.EmployeeResponse;
import com.accenture.ems.emstraining.model.InternHiringStatusResponse;
import com.accenture.ems.emstraining.model.InternStaffingResponse;
import com.accenture.ems.emstraining.model.InternStaffingSummaryResponse;
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
    private InternStaffingMapper internStaffingMapper;

    @InjectMocks
    private InternStaffingServiceImpl internStaffingService;

    private InternStaffing internStaffing;
    private InternStaffingResponse internStaffingResponse;
    private InternHiringStatusResponse internHiringStatusResponse;
    private EmployeeResponse employeeResponse1;

    @BeforeEach
    void setUp() {
        internStaffing = new InternStaffing();
        internStaffing.setId(1L);

        employeeResponse1 = new EmployeeResponse();
        employeeResponse1.setEmployeeId(1L);
        employeeResponse1.setName("Name1");
        employeeResponse1.setSurname("Surname1");
        employeeResponse1.setStartDate("Start1");
        employeeResponse1.setEndDate("End1");

        internHiringStatusResponse = new InternHiringStatusResponse();
        internHiringStatusResponse.setId(2L);
        internHiringStatusResponse.setStatus("Status2");

        internStaffingResponse = new InternStaffingResponse();
        internStaffingResponse.setEmployee(employeeResponse1);
        internStaffingResponse.setId(1L);
        internStaffingResponse.setInternHiringStatus(internHiringStatusResponse);
        internStaffingResponse.setExtension("Extension1");
        internStaffingResponse.setInternshipWorkload(1L);
        internStaffingResponse.setWorkload(1L);
    }


    @Test
    @DisplayName("getById Should return staffing response when ID exists")
    void getByIdShouldReturnStaffingWhenIdExists() {
        when(internStaffingRepository.findById(1L)).thenReturn(Optional.of(internStaffing));
        when(internStaffingMapper.toResponse(internStaffing)).thenReturn(internStaffingResponse);

        InternStaffingResponse result = internStaffingService.getById(1L);

        assertThat(result).isEqualTo(internStaffingResponse);

        verify(internStaffingRepository, times(1)).findById(1L);
        verify(internStaffingMapper, times(1)).toResponse(internStaffing);
    }

    @Test
    @DisplayName("getById should throw ResourceNotFoundException when ID does not exist")
    void getByIdShouldThrowWhenIdDoesntExist() {
        when(internStaffingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> internStaffingService.getById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Intern Staffing with id: 1 not found");

        verify(internStaffingRepository, times(1)).findById(1L);
        verify(internStaffingMapper, never()).toResponse(any());
        verifyNoMoreInteractions(internStaffingRepository, internStaffingMapper);
    }

    @Test
    @DisplayName("getAll should return empty list when no staffings exist")
    void getAllShouldReturnEmptyListWhenNoStaffings() {
        when(internStaffingRepository.findAll()).thenReturn(Collections.emptyList());

        assertThat(internStaffingService.getAll()).isEmpty();
    }

    @Test
    @DisplayName("getAll should return a list of all staffings")
    void getAllShouldReturnAllStaffings() {
        InternStaffing internStaffing2 = new InternStaffing();
        internStaffing2.setId(2L);
        InternStaffingSummaryResponse internStaffingSummaryResponse = new InternStaffingSummaryResponse();
        internStaffingSummaryResponse.setId(1L);
        internStaffingSummaryResponse.setFullName("Surname1, Name1");
        InternStaffingSummaryResponse internStaffingSummaryResponse2 = new InternStaffingSummaryResponse();
        internStaffingSummaryResponse2.setId(2L);
        internStaffingSummaryResponse2.setFullName("Surname2, Name2");

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
        when(internStaffingRepository.existsById(1L)).thenReturn(true);
        when(internProjectHistoryRepository.existsByInternStaffingId(1L)).thenReturn(false);

        internStaffingService.delete(1L);

        verify(internStaffingRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("delete should throw if staffing has history")
    void deleteShouldThrowIfStaffingHasHistory() {
        when(internStaffingRepository.existsById(1L)).thenReturn(true);
        when(internProjectHistoryRepository.existsByInternStaffingId(1L)).thenReturn(true);

        assertThatExceptionOfType(StaffingHasProjectHistoryException.class)
                .isThrownBy(() -> internStaffingService.delete(1L));
        verify(internStaffingRepository, never()).deleteById(1L);
    }

    @Test
    @DisplayName("delete should throw if staffing does not exist")
    void deleteShouldThrowIfStaffingDoesNotExist() {
        when(internStaffingRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> internStaffingService.delete(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Intern Staffing with id: 1 not found");
        verify(internStaffingRepository, never()).deleteById(1L);
    }
}
