package com.accenture.ems.emstraining.controller;


import com.accenture.ems.emstraining.exception.GlobalExceptionHandler;
import com.accenture.ems.emstraining.exception.ResourceNotFoundException;
import com.accenture.ems.emstraining.exception.StaffingHasProjectHistoryException;
import com.accenture.ems.emstraining.model.*;
import com.accenture.ems.emstraining.service.InternStaffingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InternStaffingController.class)
@Import(GlobalExceptionHandler.class)
@DisplayName("InternStaffingController tests")
class InternStaffingControllerTest {

    private static final String BASE_URL = "/api/intern/staffing";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private InternStaffingService internStaffingService;

    private InternStaffingSummaryResponse internStaffingSummaryResponse;
    private InternStaffingResponse internStaffingResponse;

    @BeforeEach
    void setUp() {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setEmployeeId(1L);
        employeeResponse.setName("Name1");
        employeeResponse.setSurname("Surname1");

        InternHiringStatusResponse statusResponse = new InternHiringStatusResponse();
        statusResponse.setId(2L);
        statusResponse.setStatus("Status2");

        internStaffingResponse = new InternStaffingResponse();
        internStaffingResponse.setId(1L);
        internStaffingResponse.setEmployee(employeeResponse);
        internStaffingResponse.setInternHiringStatus(statusResponse);
        internStaffingResponse.setInternshipWorkload(1L);
        internStaffingResponse.setWorkload(10L);
        internStaffingResponse.setExtension(LocalDateTime.parse("2026-10-01T00:00:00"));

        internStaffingSummaryResponse = new InternStaffingSummaryResponse();
        internStaffingSummaryResponse.setId(1L);
        internStaffingSummaryResponse.setEmployeeId(1L);
        internStaffingSummaryResponse.setFullName("Surname1, Name1");
        internStaffingSummaryResponse.setInternHiringStatus("Status2");
        internStaffingSummaryResponse.setExtension(LocalDateTime.parse("2026-10-01T00:00:00"));
        internStaffingSummaryResponse.setInternshipWorkload(1L);
        internStaffingSummaryResponse.setWorkload(10L);

    }

    @Test
    @DisplayName("GET /api/intern/staffing should return 200 OK and list of summary responses")
    void getInternStaffingShouldReturn200AndListOfSummaryResponses() throws Exception {
        when(internStaffingService.getAll()).thenReturn(Arrays.asList(internStaffingSummaryResponse));

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].fullName").value("Surname1, Name1"));

        verify(internStaffingService, times(1)).getAll();
    }

    @Test
    @DisplayName("GET /api/intern/staffing should return 200 OK and empty list when no staffings exist")
    void getInternStaffingShouldReturn200AndEmptyListWhenNoStaffings() throws Exception {
        when(internStaffingService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(internStaffingService, times(1)).getAll();
    }

    @Test
    @DisplayName("GET /api/intern/staffing/{id} should return 200 OK and staffing response when staffing exists")
    void getInternStaffingShouldReturn200AndStaffingResponseWhenStaffingExists() throws Exception {
        when(internStaffingService.getById(1L)).thenReturn(internStaffingResponse);

        mockMvc.perform(get(BASE_URL + "/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.employee.name").value("Name1"))
                .andExpect(jsonPath("$.internHiringStatus.id").value(2L));

        verify(internStaffingService, times(1)).getById(1L);
    }

    @Test
    @DisplayName("GET /api/intern/staffing/{id} should return 404 Not Found when staffing does not exist")
    void getInternStaffingShouldReturn404WhenStaffingDoesNotExist() throws Exception {
        when(internStaffingService.getById(42L))
                .thenThrow(new ResourceNotFoundException("Intern Staffing with id: 42 not found"));

        mockMvc.perform(get(BASE_URL + "/{id}", 42L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Intern Staffing with id: 42 not found"));

        verify(internStaffingService, times(1)).getById(42L);
    }

    @Test
    @DisplayName("POST /api/intern/staffing should return 201 Created when request is valid")
    void createInternStaffingShouldReturn201CreatedWhenRequestIsValid() throws Exception {
        InternStaffingRequest request = new InternStaffingRequest();
        request.setEmployeeId(1L);
        request.setInternHiringStatusId(2L);
        request.setWorkload(1L);
        request.setInternshipWorkload(10L);
        request.setExtension(LocalDateTime.parse("2026-10-01T00:00:00"));

        when(internStaffingService.create(request)).thenReturn(internStaffingResponse);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.employee.name").value("Name1"))
                .andExpect(jsonPath("$.internHiringStatus.id").value(2L));

        verify(internStaffingService, times(1)).create(request);
    }

    @Test
    @DisplayName("POST /api/intern/staffing should return 400 Bad Request when required fields are missing")
    void createInternStaffingShouldReturn400BadRequestWhenRequiredFieldsAreMissing() throws Exception {
        InternStaffingRequest request = new InternStaffingRequest();

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.messages.extension").exists())
                .andExpect(jsonPath("$.messages.internHiringStatusId").exists())
                .andExpect(jsonPath("$.messages.employeeId").exists());

        verify(internStaffingService, never()).create(request);
    }

    @Test
    @DisplayName("POST /api/intern/staffing should return 404 Not Found when employee does not exist")
    void createInternStaffingShouldReturn404NotFoundWhenEmployeeDoesNotExist() throws Exception {
        InternStaffingRequest request = new InternStaffingRequest();
        request.setEmployeeId(42L);
        request.setInternHiringStatusId(2L);
        request.setExtension(LocalDateTime.parse("2026-10-01T00:00:00"));

        when(internStaffingService.create(request))
                .thenThrow(new ResourceNotFoundException("Intern Staffing with id: 42 not found"));

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Intern Staffing with id: 42 not found"));

    }

    @Test
    @DisplayName("PATCH /api/intern/staffing/{id} should return 200 OK and updated staffing")
    void updateInternStaffingShouldReturn200AndUpdatedStaffingWhenRequestIsValid() throws Exception {
        InternStaffingPatchRequest request = new InternStaffingPatchRequest();
        request.setWorkload(42L);

        when(internStaffingService.update(1L, request)).thenReturn(internStaffingResponse);

        mockMvc.perform(patch(BASE_URL + "/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(internStaffingService, times(1)).update(1L, request);
    }

    @Test
    @DisplayName("PATCH /api/intern/staffing/{id} should return 404 Not Found when staffing does not exist")
    void updateInternStaffingShouldReturn404NotFoundWhenStaffingDoesNotExist() throws Exception {
        InternStaffingPatchRequest request = new InternStaffingPatchRequest();

        when(internStaffingService.update(42L, request))
                .thenThrow(new ResourceNotFoundException("Intern Staffing with id: 42 not found"));

        mockMvc.perform(patch(BASE_URL + "/{id}", 42L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Intern Staffing with id: 42 not found"));
    }

    @Test
    @DisplayName("DELETE /api/intern/staffing/{id} should return 204 No Content when deleted")
    void deleteInternStaffingShouldReturn204NoContentWhenDeleted() throws Exception {
        doNothing().when(internStaffingService).delete(1L);

        mockMvc.perform(delete(BASE_URL + "/{id}", 1L))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(internStaffingService, times(1)).delete(1L);
    }

    @Test
    @DisplayName("DELETE /api/intern/staffing/{id} should return 404 Not Found when staffing does not exist")
    void deleteInternStaffingShouldReturn404NotFoundWhenStaffingDoesNotExist() throws Exception {
        doThrow(new ResourceNotFoundException("Intern Staffing with id: 42 not found"))
                .when(internStaffingService).delete(42L);

        mockMvc.perform(delete(BASE_URL + "/{id}", 42L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Intern Staffing with id: 42 not found"));

        verify(internStaffingService, times(1)).delete(42L);
    }

    @Test
    @DisplayName("DELETE /api/intern/staffing/{id} should return 409 Conflict when staffing has project history")
    void deleteInternStaffingShouldReturn409ConflictWhenStaffingHasProjectHistory() throws Exception {
        doThrow(new StaffingHasProjectHistoryException("Cannot delete intern staffing with id: 42 because it has project history"))
                .when(internStaffingService).delete(42L);

        mockMvc.perform(delete(BASE_URL + "/{id}", 42L))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.message").value("Cannot delete intern staffing with id: 42 because it has project history"));

        verify(internStaffingService, times(1)).delete(42L);
    }
}
