package com.accenture.ems.emstraining.controller;

import com.accenture.ems.emstraining.model.InternHiringStatusRequest;
import com.accenture.ems.emstraining.model.InternHiringStatusResponse;
import com.accenture.ems.emstraining.service.InternHiringStatusService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/intern-hiring-status")
@RequiredArgsConstructor
@Slf4j
public class InternHiringStatusController {

    private final InternHiringStatusService internHiringStatusService;

    @Operation(summary = "Get hiring status by id")
    @ApiResponses(value={
            @ApiResponse(code = 200, message = "Intern hiring status found successfully"),
            @ApiResponse(code = 404, message = "Intern hiring status not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<InternHiringStatusResponse> getById(@PathVariable Long id) {
        log.info("Getting Intern Hiring Status with id: {}", id);

        Optional<InternHiringStatusResponse> result =
                internHiringStatusService.getById(id);

        if (result.isPresent()) {
            log.info("Intern hiring status found with id: {}", id);
            return ResponseEntity.ok(result.get());
        }
        log.warn("Cannot get intern hiring status. No record found with id: {}", id);
        return ResponseEntity.notFound().build();

    }

    @Operation(summary = "Find all InternHiringStatus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Intern hiring status found successfully"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<InternHiringStatusResponse>> getAll() {
        log.info("Getting all Intern Hiring Status");
        return ResponseEntity.ok(internHiringStatusService.getAll());
    }

    @Operation(summary = "Create new hiring status")
    @ApiResponses(value={
            @ApiResponse(code = 200, message = "Intern hiring status saved successfully"),
            @ApiResponse(code = 400, message = "Invalid request body"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody InternHiringStatusRequest request) {
        log.info("Creating Intern Hiring Status with request: {}", request);
        internHiringStatusService.create(request);
        log.info("Intern hiring status saved successfully");
        return ResponseEntity.status(HttpStatus.OK).body("Intern hiring status saved successfully");
    }

    @Operation(summary = "Update hiring status by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Intern hiring status updated successfully"),
            @ApiResponse(code = 400, message = "Invalid request body"),
            @ApiResponse(code = 404, message = "Intern hiring status not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id,
                                                             @Valid @RequestBody InternHiringStatusRequest request) {
        log.info("Updating Intern Hiring Status with id: {}", id);
        boolean updated = internHiringStatusService.update(id, request);
        if (updated) {
            log.info("Intern hiring status updated successfully with id: {}", id);
            return ResponseEntity.status(HttpStatus.OK).body("Intern hiring status updated successfully");
        } else {
            log.warn("Cannot update intern hiring status. No record found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete hiring status by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Intern hiring status deleted successfully"),
            @ApiResponse(code = 404, message = "Intern hiring status not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        log.info("Deleting Intern Hiring Status with id: {}", id);
        boolean deleted = internHiringStatusService.delete(id);
        if (deleted) {
            log.info("Intern hiring status deleted successfully with id: {}", id);
            return ResponseEntity.status(HttpStatus.OK).body("Intern hiring status deleted successfully");
        } else {
            log.warn("Cannot delete intern hiring status. No record found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}

