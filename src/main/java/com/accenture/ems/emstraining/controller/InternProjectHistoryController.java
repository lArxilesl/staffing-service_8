package com.accenture.ems.emstraining.controller;

import com.accenture.ems.emstraining.model.InternProjectHistoryRequestDto;
import com.accenture.ems.emstraining.model.InternProjectHistoryResponseDto;
import com.accenture.ems.emstraining.service.InternProjectHistoryService;
import com.accenture.ems.emstraining.swagger.HTMLResponseMessages;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class InternProjectHistoryController {

    private final InternProjectHistoryService internProjectHistoryService;

    @ApiOperation(value = "Get all InternProjectHistories")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HTMLResponseMessages.HTTP_200),
            @ApiResponse(code = 500, message = HTMLResponseMessages.HTTP_500)

    })
    @GetMapping("/intern/project/history")
    public ResponseEntity<List<InternProjectHistoryResponseDto>> getAll(){
        log.info("Fetching all InternProjectHistories");
        List<InternProjectHistoryResponseDto> result = internProjectHistoryService.getAll();
        log.info("Returning all InternProjectHistories");
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "Get an InternProjectHistory by Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HTMLResponseMessages.HTTP_200),
            @ApiResponse(code = 404, message = HTMLResponseMessages.HTTP_404),
            @ApiResponse(code = 500, message = HTMLResponseMessages.HTTP_500)

    })
    @GetMapping("/intern/project/history/{id}")
    public ResponseEntity<InternProjectHistoryResponseDto> getById(@PathVariable Long id){
        log.info("Request to get InternProjectHistory with id {}", id);
        Optional<InternProjectHistoryResponseDto> result = internProjectHistoryService.getById(id);

        if(result.isPresent()){
            log.info("Returnin InternProjectHistory with id: {}", id);
            return ResponseEntity.ok(result.get());
        } else {
            log.warn("InternProjectHistory with id {} was not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Create a new InternProjectHistory")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = HTMLResponseMessages.HTTP_201),
            @ApiResponse(code = 400, message = HTMLResponseMessages.HTTP_400),
            @ApiResponse(code = 500, message = HTMLResponseMessages.HTTP_500)

    })
    @PostMapping("/intern/project/history")
    public ResponseEntity<InternProjectHistoryResponseDto> createInternProjectHistory(@RequestBody InternProjectHistoryRequestDto internProjectHistoryRequestDto){
        log.info("Request to create a new InternProjectHistory entry: {}", internProjectHistoryRequestDto);
        InternProjectHistoryResponseDto created = internProjectHistoryService.create(internProjectHistoryRequestDto);
        log.info("Created a new InternProjectHistory entry with the id: {}", created.getId());

        return ResponseEntity.ok(created);
    }

    @ApiOperation(value = "Update an InternProjectHistory")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HTMLResponseMessages.HTTP_200),
            @ApiResponse(code = 404, message = HTMLResponseMessages.HTTP_404),
            @ApiResponse(code = 500, message = HTMLResponseMessages.HTTP_500)

    })
    @PutMapping("/intern/project/history/{id}")
    public ResponseEntity<InternProjectHistoryResponseDto> updateInternProjectHistory(@PathVariable Long id, @RequestBody InternProjectHistoryRequestDto internProjectHistoryRequestDto){
        log.info("Request to update InternProjectHistory with id: {}", id);
        Optional<InternProjectHistoryResponseDto> existing = internProjectHistoryService.getById(id);
        if(existing.isPresent()){
            log.info("Successfully updated InternProjectHistory with id: {}", id);
            return ResponseEntity.ok(internProjectHistoryService.update(id, internProjectHistoryRequestDto));
        } else {
            log.warn("Did not find InternProjectHistory with id: {}, update failed", id);
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Delete an InternProjectHistory by Id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = HTMLResponseMessages.HTTP_204_WITHOUT_DATA),
            @ApiResponse(code = 404, message = HTMLResponseMessages.HTTP_404),
            @ApiResponse(code = 500, message = HTMLResponseMessages.HTTP_500)

    })
    @DeleteMapping("/intern/project/history/{id}")
    public ResponseEntity<InternProjectHistoryResponseDto> deleteInternProjectHistory(@PathVariable Long id) {
        Optional<InternProjectHistoryResponseDto> toDelete = internProjectHistoryService.getById(id);
        if (toDelete.isPresent()) {
            internProjectHistoryService.delete(id);
            return ResponseEntity.ok(toDelete.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
