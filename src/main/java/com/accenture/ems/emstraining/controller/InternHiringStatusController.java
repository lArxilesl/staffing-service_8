package com.accenture.ems.emstraining.controller;

import com.accenture.ems.emstraining.model.InternHiringStatusRequest;
import com.accenture.ems.emstraining.model.InternHiringStatusResponse;
import com.accenture.ems.emstraining.service.InternHiringStatusService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/intern-hiring-statuses")
@RequiredArgsConstructor
public class InternHiringStatusController {

    private final InternHiringStatusService internHiringStatusService;

    @Operation(summary = "Get hiring status by id")
    @GetMapping("/{id}")
    public ResponseEntity<InternHiringStatusResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(internHiringStatusService.getById(id));
    }

    @Operation(summary = "Find all InternHiringStatuses")
    @GetMapping
    public ResponseEntity<List<InternHiringStatusResponse>> getAll() {
        return ResponseEntity.ok(internHiringStatusService.getAll());
    }

    @Operation(summary = "Create new hiring status")
    @PostMapping
    public ResponseEntity<InternHiringStatusResponse> create(@Valid @RequestBody InternHiringStatusRequest request) {
        InternHiringStatusResponse response = internHiringStatusService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Update hiring status by id")
    @PutMapping("/{id}")
    public ResponseEntity<InternHiringStatusResponse> update(@PathVariable Long id,
                                                             @Valid @RequestBody InternHiringStatusRequest request) {
        return ResponseEntity.ok(internHiringStatusService.update(id, request));
    }

    @Operation(summary = "Delete hiring status by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        internHiringStatusService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

