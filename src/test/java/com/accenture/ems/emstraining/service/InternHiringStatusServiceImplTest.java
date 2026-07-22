package com.accenture.ems.emstraining.service;

import com.accenture.ems.emstraining.entity.InternHiringStatusEntity;
import com.accenture.ems.emstraining.exception.ResourceNotFoundException;
import com.accenture.ems.emstraining.mapper.InternHiringStatusMapper;
import com.accenture.ems.emstraining.model.InternHiringStatusRequest;
import com.accenture.ems.emstraining.model.InternHiringStatusResponse;
import com.accenture.ems.emstraining.repository.InternHiringStatusRepository;
import com.accenture.ems.emstraining.service.impl.InternHiringStatusServiceImpl;
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
class InternHiringStatusServiceImplTest {

    @Mock
    private InternHiringStatusRepository internHiringStatusRepository;

    @Mock
    private InternHiringStatusMapper internHiringStatusMapper;

    @InjectMocks
    private InternHiringStatusServiceImpl internHiringStatusService;

    //Here the method used is AAA means Arrange Act Assert
    @Test
    void getById_ShouldReturnResponse_WhenEntityExists() {
        Long id = 1L;
        InternHiringStatusEntity entity = new InternHiringStatusEntity(id, "Good Status");
        InternHiringStatusResponse response = new InternHiringStatusResponse(id, "Good Status");

        when(internHiringStatusRepository.findById(id)).thenReturn(Optional.of(entity));
        when(internHiringStatusMapper.toResponse(entity)).thenReturn(response);

        InternHiringStatusResponse result = internHiringStatusService.getById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Good Status", result.getStatus());

        verify(internHiringStatusRepository).findById(id);
        verify(internHiringStatusMapper).toResponse(entity);
    }

    @Test
    void getById_ShouldThrowResourceNotFoundException_WhenEntityDoesNotExist() {
        Long id = 99L;

        when(internHiringStatusRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> internHiringStatusService.getById(id)
        );

        assertEquals("Intern Hiring Status not found with id: 99", exception.getMessage());

        verify(internHiringStatusRepository).findById(id);
        verifyZeroInteractions(internHiringStatusMapper);
    }

    @Test
    void getAll_ShouldReturnResponseList_WhenEntitiesExist() {
        InternHiringStatusEntity entity1 = new InternHiringStatusEntity(1L, "Good Status");
        InternHiringStatusEntity entity2 = new InternHiringStatusEntity(2L, "Bad Status");

        List<InternHiringStatusEntity> entities = Arrays.asList(entity1, entity2);

        InternHiringStatusResponse response1 = new InternHiringStatusResponse(1L, "Good Status");
        InternHiringStatusResponse response2 = new InternHiringStatusResponse(2L, "Bad Status");

        List<InternHiringStatusResponse> responses = Arrays.asList(response1, response2);

        when(internHiringStatusRepository.findAll()).thenReturn(entities);
        when(internHiringStatusMapper.toResponseList(entities)).thenReturn(responses);

        List<InternHiringStatusResponse> result = internHiringStatusService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Good Status", result.get(0).getStatus());
        assertEquals("Bad Status", result.get(1).getStatus());

        verify(internHiringStatusRepository).findAll();
        verify(internHiringStatusMapper).toResponseList(entities);
    }

    @Test
    void getAll_ShouldReturnEmptyList_WhenNoEntitiesExist() {
        List<InternHiringStatusEntity> entities = Collections.emptyList();
        List<InternHiringStatusResponse> responses = Collections.emptyList();

        when(internHiringStatusRepository.findAll()).thenReturn(entities);
        when(internHiringStatusMapper.toResponseList(entities)).thenReturn(responses);

        List<InternHiringStatusResponse> result = internHiringStatusService.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(internHiringStatusRepository).findAll();
        verify(internHiringStatusMapper).toResponseList(entities);
    }

    @Test
    void create_ShouldSaveEntityAndReturnResponse() {
        InternHiringStatusRequest request = new InternHiringStatusRequest("Interview Scheduled");
        InternHiringStatusEntity entity = new InternHiringStatusEntity(null, "Interview Scheduled");

        when(internHiringStatusMapper.toEntity(request)).thenReturn(entity);

        internHiringStatusService.create(request);

        verify(internHiringStatusMapper).toEntity(request);
        verify(internHiringStatusRepository).save(entity);
        verify(internHiringStatusMapper, never()).toResponse(any(InternHiringStatusEntity.class));
    }

    @Test
    void update_ShouldUpdateEntityAndReturnResponse_WhenEntityExists() {
        Long id = 1L;
        InternHiringStatusRequest request = new InternHiringStatusRequest("Updated Status");
        InternHiringStatusEntity entity = new InternHiringStatusEntity(id, "Old Status");

        when(internHiringStatusRepository.findById(id)).thenReturn(Optional.of(entity));

        doAnswer(invocation -> {
            InternHiringStatusEntity target = invocation.getArgument(1);
            target.setStatus(request.getStatus());
            return null;
        }).when(internHiringStatusMapper).updateEntityFromRequest(request, entity);

        internHiringStatusService.update(id, request);

        assertEquals("Updated Status", entity.getStatus());

        verify(internHiringStatusRepository).findById(id);
        verify(internHiringStatusMapper).updateEntityFromRequest(request, entity);
        verify(internHiringStatusRepository).save(entity);
        verify(internHiringStatusMapper, never()).toResponse(any(InternHiringStatusEntity.class));
    }

    @Test
    void update_ShouldThrowResourceNotFoundException_WhenEntityDoesNotExist() {
        Long id = 99L;
        InternHiringStatusRequest request = new InternHiringStatusRequest("Updated Status");

        when(internHiringStatusRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> internHiringStatusService.update(id, request));

        verify(internHiringStatusRepository).findById(id);
        verify(internHiringStatusMapper, never()).updateEntityFromRequest(any(), any());
        verify(internHiringStatusRepository, never()).save(any(InternHiringStatusEntity.class));
    }

    @Test
    void delete_ShouldDeleteEntity_WhenEntityExists() {
        Long id = 1L;
        InternHiringStatusEntity entity = new InternHiringStatusEntity(id, "Good Status");

        when(internHiringStatusRepository.findById(id)).thenReturn(Optional.of(entity));

        internHiringStatusService.delete(id);

        verify(internHiringStatusRepository).findById(id);
        verify(internHiringStatusRepository).delete(entity);
    }

    @Test
    void delete_ShouldThrowResourceNotFoundException_WhenEntityDoesNotExist() {
        Long id = 99L;

        when(internHiringStatusRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> internHiringStatusService.delete(id)
        );

        assertEquals("Intern Hiring Status not found with id: 99", exception.getMessage());

        verify(internHiringStatusRepository).findById(id);
        verify(internHiringStatusRepository, never()).delete(any());
    }
}