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
@RequestMapping("/api/intern-hiring-status")
@RequiredArgsConstructor
public class InternHiringStatusController {

    private final InternHiringStatusService internHiringStatusService;

    @Operation(summary = "Get hiring status by id")
    @GetMapping("/get/{id}")
    public ResponseEntity<InternHiringStatusResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(internHiringStatusService.getById(id));
    }

    @Operation(summary = "Find all InternHiringStatus")
    @GetMapping("/get-all")
    public ResponseEntity<List<InternHiringStatusResponse>> getAll() {
        return ResponseEntity.ok(internHiringStatusService.getAll());
    }

    @Operation(summary = "Create new hiring status")
    @PostMapping("/create")
    public ResponseEntity<String> create(@Valid @RequestBody InternHiringStatusRequest request) {
        internHiringStatusService.create(request);
        return ResponseEntity.status(HttpStatus.OK).body("Intern hiring status saved successfully");
    }

    @Operation(summary = "Update hiring status by id")
    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id,
                                                             @Valid @RequestBody InternHiringStatusRequest request) {
        internHiringStatusService.update(id, request);
        return ResponseEntity.status(HttpStatus.OK).body("Intern hiring status updated successfully");
    }

    @Operation(summary = "Delete hiring status by id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        internHiringStatusService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Intern hiring status deleted successfully");
    }
}

