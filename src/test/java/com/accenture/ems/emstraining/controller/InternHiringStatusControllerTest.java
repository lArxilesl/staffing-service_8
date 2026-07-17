package com.accenture.ems.emstraining.controller;

import com.accenture.ems.emstraining.exception.GlobalExceptionHandler;
import com.accenture.ems.emstraining.exception.ResourceNotFoundException;
import com.accenture.ems.emstraining.model.InternHiringStatusRequest;
import com.accenture.ems.emstraining.model.InternHiringStatusResponse;
import com.accenture.ems.emstraining.service.InternHiringStatusService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(InternHiringStatusController.class)
@Import(GlobalExceptionHandler.class)
class InternHiringStatusControllerTest {

    private static final String BASE_URL = "/api/intern-hiring-statuses";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private InternHiringStatusService internHiringStatusService;

    @Test
    void getById_ShouldReturnOk() throws Exception {
        InternHiringStatusResponse response = new InternHiringStatusResponse(1L, "Good Status");

        when(internHiringStatusService.getById(1L)).thenReturn(response);

        mockMvc.perform(get(BASE_URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value("Good Status"));
    }

    @Test
    void getAll_ShouldReturnOk() throws Exception {
        InternHiringStatusResponse response = new InternHiringStatusResponse(1L, "Good Status");

        when(internHiringStatusService.getAll()).thenReturn(Collections.singletonList(response));

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].status").value("Good Status"));
    }

    @Test
    void create_ShouldReturnCreated() throws Exception {
        InternHiringStatusRequest request = new InternHiringStatusRequest("New Status");
        InternHiringStatusResponse response = new InternHiringStatusResponse(4L, "New Status");

        when(internHiringStatusService.create(any(InternHiringStatusRequest.class))).thenReturn(response);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(4L))
                .andExpect(jsonPath("$.status").value("New Status"));
    }

    @Test
    void update_ShouldReturnOk() throws Exception {
        InternHiringStatusRequest request = new InternHiringStatusRequest("Updated Status");
        InternHiringStatusResponse response = new InternHiringStatusResponse(1L, "Updated Status");

        when(internHiringStatusService.update(eq(1L), any(InternHiringStatusRequest.class))).thenReturn(response);

        mockMvc.perform(put(BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value("Updated Status"));
    }

    @Test
    void delete_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/1"))
                .andExpect(status().isNoContent());

        verify(internHiringStatusService).delete(1L);
    }

    @Test
    void getById_ShouldReturnNotFound() throws Exception {
        when(internHiringStatusService.getById(99L))
                .thenThrow(new ResourceNotFoundException("Intern Hiring Status not found with id: 99"));

        mockMvc.perform(get(BASE_URL + "/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Intern Hiring Status not found with id: 99"));
    }

    @Test
    void create_ShouldReturnBadRequest_WhenStatusIsBlank() throws Exception {
        InternHiringStatusRequest request = new InternHiringStatusRequest("");

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verifyZeroInteractions(internHiringStatusService);
    }

    @Test
    void update_ShouldReturnBadRequest_WhenStatusIsBlank() throws Exception {
        InternHiringStatusRequest request = new InternHiringStatusRequest("");

        mockMvc.perform(put(BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verifyZeroInteractions(internHiringStatusService);
    }
}