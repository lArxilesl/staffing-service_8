package com.accenture.ems.emstraining.service;

import com.accenture.ems.emstraining.entity.ProjectHiringStatusEntity;
import com.accenture.ems.emstraining.mapper.ProjectHiringStatusMapper;
import com.accenture.ems.emstraining.model.ProjectHiringStatusRequest;
import com.accenture.ems.emstraining.model.ProjectHiringStatusResponse;
import com.accenture.ems.emstraining.repository.ProjectHiringStatusRepository;
import com.accenture.ems.emstraining.service.impl.ProjectHiringStatusServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectHiringStatusServiceImplTest {

    @Mock
    private ProjectHiringStatusRepository projectHiringStatusRepository;

    @Mock
    private ProjectHiringStatusMapper projectHiringStatusMapper;

    @InjectMocks
    private ProjectHiringStatusServiceImpl projectHiringStatusService;

    // Here the method used is AAA means Arrange Act Assert
    @Test
    void getById_ShouldReturnResponse_WhenEntityExists() {
        Long id = 1L;
        ProjectHiringStatusEntity entity =
                new ProjectHiringStatusEntity(id, "Good Status");
        ProjectHiringStatusResponse response =
                new ProjectHiringStatusResponse(id, "Good Status");

        when(projectHiringStatusRepository.findById(id))
                .thenReturn(Optional.of(entity));
        when(projectHiringStatusMapper.toResponse(entity))
                .thenReturn(response);

        Optional<ProjectHiringStatusResponse> result =
                projectHiringStatusService.getById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        assertEquals("Good Status", result.get().getStatus());

        verify(projectHiringStatusRepository).findById(id);
        verify(projectHiringStatusMapper).toResponse(entity);
    }

    @Test
    void getById_ShouldReturnEmpty_WhenEntityDoesNotExist() {
        Long id = 99L;

        when(projectHiringStatusRepository.findById(id))
                .thenReturn(Optional.empty());

        Optional<ProjectHiringStatusResponse> result =
                projectHiringStatusService.getById(id);

        assertFalse(result.isPresent());

        verify(projectHiringStatusRepository).findById(id);
        verifyZeroInteractions(projectHiringStatusMapper);
    }

    @Test
    void getAll_ShouldReturnResponseList_WhenEntitiesExist() {
        ProjectHiringStatusEntity entity1 =
                new ProjectHiringStatusEntity(1L, "Good Status");
        ProjectHiringStatusEntity entity2 =
                new ProjectHiringStatusEntity(2L, "Bad Status");

        List<ProjectHiringStatusEntity> entities =
                Arrays.asList(entity1, entity2);

        ProjectHiringStatusResponse response1 =
                new ProjectHiringStatusResponse(1L, "Good Status");
        ProjectHiringStatusResponse response2 =
                new ProjectHiringStatusResponse(2L, "Bad Status");

        List<ProjectHiringStatusResponse> responses =
                Arrays.asList(response1, response2);

        when(projectHiringStatusRepository.findAll())
                .thenReturn(entities);
        when(projectHiringStatusMapper.toResponseList(entities))
                .thenReturn(responses);

        List<ProjectHiringStatusResponse> result =
                projectHiringStatusService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Good Status", result.get(0).getStatus());
        assertEquals("Bad Status", result.get(1).getStatus());

        verify(projectHiringStatusRepository).findAll();
        verify(projectHiringStatusMapper).toResponseList(entities);
    }

    @Test
    void getAll_ShouldReturnEmptyList_WhenNoEntitiesExist() {
        List<ProjectHiringStatusEntity> entities =
                Collections.emptyList();
        List<ProjectHiringStatusResponse> responses =
                Collections.emptyList();

        when(projectHiringStatusRepository.findAll())
                .thenReturn(entities);
        when(projectHiringStatusMapper.toResponseList(entities))
                .thenReturn(responses);

        List<ProjectHiringStatusResponse> result =
                projectHiringStatusService.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(projectHiringStatusRepository).findAll();
        verify(projectHiringStatusMapper).toResponseList(entities);
    }

    @Test
    void create_ShouldSaveEntityAndReturnResponse() {
        ProjectHiringStatusRequest request =
                new ProjectHiringStatusRequest("Interview Scheduled");
        ProjectHiringStatusEntity entity =
                new ProjectHiringStatusEntity(null, "Interview Scheduled");

        when(projectHiringStatusMapper.toEntity(request))
                .thenReturn(entity);

        projectHiringStatusService.create(request);

        verify(projectHiringStatusMapper).toEntity(request);
        verify(projectHiringStatusRepository).save(entity);
        verify(projectHiringStatusMapper, never())
                .toResponse(any(ProjectHiringStatusEntity.class));
    }

    @Test
    void update_ShouldUpdateEntityAndReturnResponse_WhenEntityExists() {
        Long id = 1L;
        ProjectHiringStatusRequest request =
                new ProjectHiringStatusRequest("Updated Status");
        ProjectHiringStatusEntity entity =
                new ProjectHiringStatusEntity(id, "Old Status");

        when(projectHiringStatusRepository.findById(id))
                .thenReturn(Optional.of(entity));

        doAnswer(invocation -> {
            ProjectHiringStatusEntity target = invocation.getArgument(1);
            target.setStatus(request.getStatus());
            return null;
        }).when(projectHiringStatusMapper)
                .updateEntityFromRequest(request, entity);

        projectHiringStatusService.update(id, request);

        assertEquals("Updated Status", entity.getStatus());

        verify(projectHiringStatusRepository).findById(id);
        verify(projectHiringStatusMapper)
                .updateEntityFromRequest(request, entity);
        verify(projectHiringStatusRepository).save(entity);
        verify(projectHiringStatusMapper, never())
                .toResponse(any(ProjectHiringStatusEntity.class));
    }

    @Test
    void update_ShouldReturnFalse_WhenEntityDoesNotExist() {
        Long id = 99L;
        ProjectHiringStatusRequest request =
                new ProjectHiringStatusRequest("Updated Status");

        when(projectHiringStatusRepository.findById(id))
                .thenReturn(Optional.empty());

        boolean result =
                projectHiringStatusService.update(id, request);

        assertFalse(result);

        verify(projectHiringStatusRepository).findById(id);
        verify(projectHiringStatusRepository, never()).save(any());
        verifyZeroInteractions(projectHiringStatusMapper);
    }

    @Test
    void delete_ShouldDeleteEntity_WhenEntityExists() {
        Long id = 1L;
        ProjectHiringStatusEntity entity =
                new ProjectHiringStatusEntity(id, "Good Status");

        when(projectHiringStatusRepository.findById(id))
                .thenReturn(Optional.of(entity));

        projectHiringStatusService.delete(id);

        verify(projectHiringStatusRepository).findById(id);
        verify(projectHiringStatusRepository).delete(entity);
    }

    @Test
    void delete_ShouldReturnFalse_WhenEntityDoesNotExist() {
        Long id = 99L;

        when(projectHiringStatusRepository.findById(id))
                .thenReturn(Optional.empty());

        boolean result =
                projectHiringStatusService.delete(id);

        assertFalse(result);

        verify(projectHiringStatusRepository).findById(id);
        verify(projectHiringStatusRepository, never()).delete(any());
    }
}