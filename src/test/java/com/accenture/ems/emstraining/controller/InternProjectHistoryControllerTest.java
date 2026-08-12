package com.accenture.ems.emstraining.controller;

import com.accenture.ems.emstraining.model.InternProjectHistoryRequestDto;
import com.accenture.ems.emstraining.model.InternProjectHistoryResponseDto;
import com.accenture.ems.emstraining.service.InternProjectHistoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = InternProjectHistoryController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class InternProjectHistoryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private InternProjectHistoryService internProjectHistoryService;
    @Autowired
    private ObjectMapper objectMapper;

    private InternProjectHistoryResponseDto createDtoForTests() {
        InternProjectHistoryResponseDto dto = new InternProjectHistoryResponseDto();
        dto.setId(1L);
        dto.setComments("Test comment - Good");
        return dto;
    }

    private InternProjectHistoryRequestDto createDtoFroTestsNoId() {
        InternProjectHistoryRequestDto dto = new InternProjectHistoryRequestDto();
        dto.setComments("Test comment - Good");
        return dto;
    }

    @Test
    void getAll_WhenCalled_Returns200AndListOfDtos() throws Exception {
        when(internProjectHistoryService.getAll()).thenReturn(Arrays.asList(createDtoForTests()));
        mockMvc.perform(get("/api/intern/project/history").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].comments").value("Test comment - Good"));
    }

    @Test
    void getById_WhenIdFound_Returns200AndDT() throws Exception{
        when(internProjectHistoryService.getById(1L)).thenReturn(Optional.of(createDtoForTests()));

        mockMvc.perform(get("/api/intern/project/history/{id}", 1L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.comments").value("Test comment - Good"));
    }

    @Test
    void getById_WhenIdNotFound_Returns404() throws Exception {
        when(internProjectHistoryService.getById(1000L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/inter/project/history/{id}", 1000L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_WhenCalled_Returns200AndCreatedDTO() throws Exception{
        when(internProjectHistoryService.create(any(InternProjectHistoryRequestDto.class)))
                .thenReturn(createDtoForTests());

        mockMvc.perform(post("/api/intern/project/history").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDtoFroTestsNoId())))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.comments").value("Test comment - Good"));
    }

    @Test
    void update_WhenIdExists_Returns200AndUpdatedDTO() throws Exception {
        when(internProjectHistoryService.getById(1L)).thenReturn(Optional.of(createDtoForTests()));
        when(internProjectHistoryService.update(eq(1L), any(InternProjectHistoryRequestDto.class)))
                .thenReturn(createDtoForTests());

        mockMvc.perform(put("/api/intern/project/history/{id}", 1L).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDtoFroTestsNoId())))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.comments").value("Test comment - Good"));
    }

    @Test
    void update_WhenIdNotFound_Returns404() throws Exception {
        when(internProjectHistoryService.getById(1000L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/intern/project/history/{id}", 1000L).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDtoFroTestsNoId())))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_WhenIdExists_Returns200() throws Exception {
        when(internProjectHistoryService.getById(1L)).thenReturn(Optional.of(createDtoForTests()));

        mockMvc.perform(delete("/api/intern/project/history/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void delete_WhenIdNotFound_Returns404() throws Exception {
        when(internProjectHistoryService.getById(1000L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/intern/project/history/{id}", 1000L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
