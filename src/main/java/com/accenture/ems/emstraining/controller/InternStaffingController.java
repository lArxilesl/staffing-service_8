package com.accenture.ems.emstraining.controller;

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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/intern/staffing")
public class InternStaffingController {

    private final InternStaffingService internStaffingService;

    @GetMapping
    public List<InternStaffingSummaryResponse> getAll() {
        log.info("Fetch all intern Staffings");
        List<InternStaffingSummaryResponse> result = internStaffingService.findAll();
        log.info("Returning all intern Staffings");

        return result;
    }

    @GetMapping("/{id}")
    public InternStaffingResponse getById(@PathVariable Long id) {

        return internStaffingService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        internStaffingService.delete(id);
        log.info("Delete intern Staffing with id: {}", id);
    }

    @PostMapping()
    public ResponseEntity<InternStaffingResponse> createStaffing(@Valid @RequestBody InternStaffingRequest internStaffingRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(internStaffingService.create(internStaffingRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<InternStaffingResponse> updateStaffing(@PathVariable Long id, @Valid @RequestBody InternStaffingPatchRequest internStaffingPatchRequest) {

        return ResponseEntity.ok(internStaffingService.update(id, internStaffingPatchRequest));
    }
}