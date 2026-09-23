package com.accenture.ems.emstraining.service;

import com.accenture.ems.emstraining.entity.InternStaffing;
import com.accenture.ems.emstraining.exception.ResourceNotFoundException;
import com.accenture.ems.emstraining.mapper.InternStaffingMapper;
import com.accenture.ems.emstraining.model.EmployeeResponse;
import com.accenture.ems.emstraining.model.InternHiringStatusResponse;
import com.accenture.ems.emstraining.model.InternStaffingResponse;
import com.accenture.ems.emstraining.repository.InternStaffingRepository;
import com.accenture.ems.emstraining.service.impl.InternStaffingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@DisplayName("InternStaffingService Tests")
@ExtendWith(MockitoExtension.class)
class InternStaffingServiceTest {
    @Mock
    private InternStaffingRepository internStaffingRepository;

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
    @DisplayName("Should return staffing response when ID exists")
    void shouldReturnStaffingWhenIdExists() {
        when(internStaffingRepository.findById(1L)).thenReturn(Optional.of(internStaffing));
        when(internStaffingMapper.toResponse(internStaffing)).thenReturn(internStaffingResponse);

        InternStaffingResponse result = internStaffingService.findById(1L);

        assertThat(result).isEqualTo(internStaffingResponse);

        verify(internStaffingRepository, times(1)).findById(1L);
        verify(internStaffingMapper, times(1)).toResponse(internStaffing);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when ID does not exist")
    void shouldThrowWhenIdDoesntExist() {
        when(internStaffingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> internStaffingService.findById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Intern Staffing with id: 1 not found");

        verify(internStaffingRepository, times(1)).findById(1L);
        verify(internStaffingMapper, never()).toResponse(any());
        verifyNoMoreInteractions(internStaffingRepository, internStaffingMapper);
    }

}
