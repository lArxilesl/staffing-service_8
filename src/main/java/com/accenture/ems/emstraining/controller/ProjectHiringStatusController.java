package com.accenture.ems.emstraining.controller;

import com.accenture.ems.emstraining.model.ProjectHiringStatusRequest;
import com.accenture.ems.emstraining.model.ProjectHiringStatusResponse;
import com.accenture.ems.emstraining.service.ProjectHiringStatusService;
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
@RequestMapping("/api/project-hiring-status")
@RequiredArgsConstructor
@Slf4j
public class ProjectHiringStatusController {

    private final ProjectHiringStatusService projectHiringStatusService;

    @Operation(summary = "Get project hiring status by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Project hiring status found successfully"),
            @ApiResponse(code = 404, message = "Project hiring status not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProjectHiringStatusResponse> getById(@PathVariable Long id) {
        log.info("Getting Project Hiring Status with id: {}", id);

        Optional<ProjectHiringStatusResponse> result =
                projectHiringStatusService.getById(id);

        if (result.isPresent()) {
            log.info("Project hiring status found with id: {}", id);
            return ResponseEntity.ok(result.get());
        }

        log.warn("Cannot get project hiring status. No record found with id: {}", id);
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Find all ProjectHiringStatus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Project hiring status found successfully"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<ProjectHiringStatusResponse>> getAll() {
        log.info("Getting all Project Hiring Status");
        return ResponseEntity.ok(projectHiringStatusService.getAll());
    }

    @Operation(summary = "Create new project hiring status")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Project hiring status saved successfully"),
            @ApiResponse(code = 400, message = "Invalid request body"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<String> create(
            @Valid @RequestBody ProjectHiringStatusRequest request) {

        log.info("Creating Project Hiring Status with request: {}", request);

        projectHiringStatusService.create(request);

        log.info("Project hiring status saved successfully");

        return ResponseEntity.status(HttpStatus.OK)
                .body("Project hiring status saved successfully");
    }

    @Operation(summary = "Update project hiring status by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Project hiring status updated successfully"),
            @ApiResponse(code = 400, message = "Invalid request body"),
            @ApiResponse(code = 404, message = "Project hiring status not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> update(
            @PathVariable Long id,
            @Valid @RequestBody ProjectHiringStatusRequest request) {

        log.info("Updating Project Hiring Status with id: {}", id);

        boolean updated = projectHiringStatusService.update(id, request);

        if (updated) {
            log.info("Project hiring status updated successfully with id: {}", id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Project hiring status updated successfully");
        } else {
            log.warn("Cannot update project hiring status. No record found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete project hiring status by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Project hiring status deleted successfully"),
            @ApiResponse(code = 404, message = "Project hiring status not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {

        log.info("Deleting Project Hiring Status with id: {}", id);

        boolean deleted = projectHiringStatusService.delete(id);

        if (deleted) {
            log.info("Project hiring status deleted successfully with id: {}", id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Project hiring status deleted successfully");
        } else {
            log.warn("Cannot delete project hiring status. No record found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}