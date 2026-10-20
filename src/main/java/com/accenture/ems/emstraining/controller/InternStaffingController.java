package com.accenture.ems.emstraining.controller;

import com.accenture.ems.emstraining.exception.EmptyPatchRequestException;
import com.accenture.ems.emstraining.model.InternStaffingPatchRequest;
import com.accenture.ems.emstraining.model.InternStaffingRequest;
import com.accenture.ems.emstraining.model.InternStaffingResponse;
import com.accenture.ems.emstraining.model.InternStaffingSummaryResponse;
import com.accenture.ems.emstraining.service.InternStaffingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/intern/staffing")
public class InternStaffingController {

    private final InternStaffingService internStaffingService;

    @GetMapping
    public List<InternStaffingSummaryResponse> getAll() {
        log.info("Fetch all intern Staffings");
        List<InternStaffingSummaryResponse> result = internStaffingService.getAll();
        log.info("Returning all intern Staffings");

        return result;
    }

    @GetMapping("/{id}")
    public ResponseEntity<InternStaffingResponse> getById(@PathVariable Long id) {
        Optional<InternStaffingResponse> result = internStaffingService.getById(id);

        if (result.isPresent()) {
            log.info("Returning intern Staffing with id: {}", id);
            return ResponseEntity.ok(result.get());
        } else {
            log.warn("Intern Staffing with id: {} was not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (!internStaffingService.existsById(id)) {
            log.warn("Intern Staffing with id: {} was not found", id);
            return ResponseEntity.notFound().build();
        }
        internStaffingService.delete(id);
        log.info("Delete intern Staffing with id: {}", id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<InternStaffingResponse> create(@Valid @RequestBody InternStaffingRequest internStaffingRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(internStaffingService.create(internStaffingRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<InternStaffingResponse> update(@PathVariable Long id, @Valid @RequestBody InternStaffingPatchRequest internStaffingPatchRequest) {
        if (internStaffingPatchRequest.isEmpty()) {
            log.warn("Patch request for Intern Staffing with id: {} contains no fields to update", id);
            throw new EmptyPatchRequestException("Patch request must contain at least one field to update");
        }
        return ResponseEntity.ok(internStaffingService.update(id, internStaffingPatchRequest));
    }
}