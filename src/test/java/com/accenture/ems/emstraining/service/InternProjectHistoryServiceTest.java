package com.accenture.ems.emstraining.service;

import com.accenture.ems.emstraining.entity.InternProjectHistory;
import com.accenture.ems.emstraining.mapper.InternProjectHistoryMapper;
import com.accenture.ems.emstraining.model.InternProjectHistoryRequestDto;
import com.accenture.ems.emstraining.model.InternProjectHistoryResponseDto;
import com.accenture.ems.emstraining.repository.InternProjectHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InternProjectHistoryServiceTest {
    @Mock
    private InternProjectHistoryRepository internProjectHistoryRepository;
    @Mock
    private InternProjectHistoryMapper internProjectHistoryMapper;
    @InjectMocks
    private InternProjectHistoryServiceImpl internProjectHistoryService;

    private InternProjectHistory sampleEntity;
    private InternProjectHistoryResponseDto sampleDto;
    private InternProjectHistoryRequestDto sampleDTONoId;

    @BeforeEach
    void setUp() {
        sampleEntity = new InternProjectHistory();
        sampleEntity.setId(1L);
        sampleEntity.setComments("Test comment - Good");

        sampleDto = new InternProjectHistoryResponseDto();
        sampleDto.setId(1L);
        sampleDto.setComments("Test comment - Good");

        sampleDTONoId = new InternProjectHistoryRequestDto();
        sampleDTONoId.setComments("Test comment - Good");
    }

    @Test
    void getAll_WhenCalled_ReturnsListOfDTOs() {
        when(internProjectHistoryRepository.findAll()).thenReturn(Arrays.asList(sampleEntity));
        when(internProjectHistoryMapper.internProjectHistoryEntityToDTO(sampleEntity)).thenReturn(sampleDto);

        List<InternProjectHistoryResponseDto> result = internProjectHistoryService.getAll();

        assertEquals(1, result.size());
        assertEquals("Test comment - Good", result.get(0).getComments());
    }

    @Test
    void getAll_WhenTableIsEmpty_ReturnsEmptyList() {
        when(internProjectHistoryRepository.findAll()).thenReturn(Collections.emptyList());

        List<InternProjectHistoryResponseDto> result = internProjectHistoryService.getAll();

        assertTrue(result.isEmpty());
        verify(internProjectHistoryMapper, never()).internProjectHistoryEntityToDTO(any());
    }

    @Test
    void getById_WhenIdExists_ReturnsAppropriateDto(){
        when(internProjectHistoryRepository.findById(1L)).thenReturn(Optional.of(sampleEntity));
        when(internProjectHistoryMapper.internProjectHistoryEntityToDTO(sampleEntity)).thenReturn(sampleDto);

        Optional<InternProjectHistoryResponseDto> result = internProjectHistoryService.getById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("Test comment - Good", result.get().getComments());
    }

    @Test
    void getById_WhenIdDoesNotExist_ReturnsEmptyOptional() {
        when(internProjectHistoryRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<InternProjectHistoryResponseDto> result = internProjectHistoryService.getById(999L);

        assertFalse(result.isPresent());
    }

    @Test
    void create_WhenCalled_ReturnsANewlyCreatedDto() {
        when(internProjectHistoryMapper.internProjectHistoryDTOtoEntity(sampleDTONoId)).thenReturn(sampleEntity);
        when(internProjectHistoryRepository.save(sampleEntity)).thenReturn(sampleEntity);
        when(internProjectHistoryMapper.internProjectHistoryEntityToDTO(sampleEntity)).thenReturn(sampleDto);

        InternProjectHistoryResponseDto result = internProjectHistoryService.create(sampleDTONoId);

        assertEquals(1L, result.getId());
        assertEquals("Test comment - Good", result.getComments());
    }

    @Test
    void create_WhenRepositoryThrowsConstraintViolation_PropagatesException() {
        when(internProjectHistoryMapper.internProjectHistoryDTOtoEntity(sampleDTONoId)).thenReturn(sampleEntity);
        when(internProjectHistoryRepository.save(sampleEntity))
                .thenThrow(new DataIntegrityViolationException("FK constraint violation"));

        assertThrows(DataIntegrityViolationException.class,
                () -> internProjectHistoryService.create(sampleDTONoId));
    }

    @Test
    void update_WhenCalled_ReturnsUpdatedDto() {
        when(internProjectHistoryRepository.findById(1L)).thenReturn(Optional.of(sampleEntity));
        when(internProjectHistoryRepository.save(sampleEntity)).thenReturn(sampleEntity);
        when(internProjectHistoryMapper.internProjectHistoryEntityToDTO(sampleEntity)).thenReturn(sampleDto);

        InternProjectHistoryResponseDto result = internProjectHistoryService.update(1L, sampleDTONoId);

        assertEquals(1L, result.getId());
        assertEquals("Test comment - Good", result.getComments());
    }

    @Test
    void update_WhenIdDoesNotExist_ThrowsException() {
        when(internProjectHistoryRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> internProjectHistoryService.update(999L, sampleDTONoId));

        verify(internProjectHistoryRepository, never()).save(any());
    }

    @Test
    void delete_WhenExistingId_DeletesEntitySuccessfully() {
        internProjectHistoryService.delete(1L);
        verify(internProjectHistoryRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_WhenIdDoesNotExist_ThrowsException() {
        doThrow(new EmptyResultDataAccessException(1))
                .when(internProjectHistoryRepository).deleteById(999L);

        assertThrows(EmptyResultDataAccessException.class,
                () -> internProjectHistoryService.delete(999L));
    }
}
