package com.accenture.ems.emstraining.controller;

import com.accenture.ems.emstraining.exception.GlobalExceptionHandler;
import com.accenture.ems.emstraining.model.ProjectHiringStatusRequest;
import com.accenture.ems.emstraining.model.ProjectHiringStatusResponse;
import com.accenture.ems.emstraining.service.ProjectHiringStatusService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProjectHiringStatusController.class)
@Import(GlobalExceptionHandler.class)
class ProjectHiringStatusControllerTest {

    private static final String BASE_URL = "/api/project-hiring-status";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProjectHiringStatusService projectHiringStatusService;

    @Test
    void getById_ShouldReturnOk() throws Exception {
        ProjectHiringStatusResponse response =
                new ProjectHiringStatusResponse(1L, "Good Status");

        when(projectHiringStatusService.getById(1L))
                .thenReturn(Optional.of(response));

        mockMvc.perform(get(BASE_URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value("Good Status"));
    }

    @Test
    void getAll_ShouldReturnOk() throws Exception {
        ProjectHiringStatusResponse response =
                new ProjectHiringStatusResponse(1L, "Good Status");

        when(projectHiringStatusService.getAll())
                .thenReturn(Collections.singletonList(response));

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].status").value("Good Status"));
    }

    @Test
    void create_ShouldReturnOk() throws Exception {
        ProjectHiringStatusRequest request =
                new ProjectHiringStatusRequest("New Status");

        doNothing().when(projectHiringStatusService)
                .create(any(ProjectHiringStatusRequest.class));

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "Project hiring status saved successfully"));

        verify(projectHiringStatusService)
                .create(any(ProjectHiringStatusRequest.class));
    }

    @Test
    void update_ShouldReturnOk() throws Exception {
        ProjectHiringStatusRequest request =
                new ProjectHiringStatusRequest("Updated Status");

        when(projectHiringStatusService.update(
                eq(1L),
                any(ProjectHiringStatusRequest.class)))
                .thenReturn(true);

        mockMvc.perform(put(BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "Project hiring status updated successfully"));

        verify(projectHiringStatusService)
                .update(eq(1L), any(ProjectHiringStatusRequest.class));
    }

    @Test
    void update_ShouldReturnNotFound() throws Exception {
        ProjectHiringStatusRequest request =
                new ProjectHiringStatusRequest("Updated Status");

        when(projectHiringStatusService.update(
                eq(99L),
                any(ProjectHiringStatusRequest.class)))
                .thenReturn(false);

        mockMvc.perform(put(BASE_URL + "/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());

        verify(projectHiringStatusService)
                .update(eq(99L), any(ProjectHiringStatusRequest.class));
    }

    @Test
    void delete_ShouldReturnOk() throws Exception {
        when(projectHiringStatusService.delete(1L))
                .thenReturn(true);

        mockMvc.perform(delete(BASE_URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "Project hiring status deleted successfully"));

        verify(projectHiringStatusService).delete(1L);
    }

    @Test
    void delete_ShouldReturnNotFound() throws Exception {
        when(projectHiringStatusService.delete(99L))
                .thenReturn(false);

        mockMvc.perform(delete(BASE_URL + "/99"))
                .andExpect(status().isNotFound());

        verify(projectHiringStatusService).delete(99L);
    }

    @Test
    void getById_ShouldReturnNotFound() throws Exception {
        when(projectHiringStatusService.getById(99L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get(BASE_URL + "/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_ShouldReturnBadRequest_WhenStatusIsBlank() throws Exception {
        ProjectHiringStatusRequest request =
                new ProjectHiringStatusRequest("");

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verifyZeroInteractions(projectHiringStatusService);
    }

    @Test
    void update_ShouldReturnBadRequest_WhenStatusIsBlank() throws Exception {
        ProjectHiringStatusRequest request =
                new ProjectHiringStatusRequest("");

        mockMvc.perform(put(BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verifyZeroInteractions(projectHiringStatusService);
    }
}