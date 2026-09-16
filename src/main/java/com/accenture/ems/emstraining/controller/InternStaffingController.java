package com.accenture.ems.emstraining.controller;

import com.accenture.ems.emstraining.model.InternStaffingResponse;
import com.accenture.ems.emstraining.model.InternStaffingSummaryResponse;
import com.accenture.ems.emstraining.repository.InternStaffingRepository;
import com.accenture.ems.emstraining.service.InternStaffingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/intern/staffing")
public class InternStaffingController {

    private final InternStaffingService internStaffingService;
    private final InternStaffingRepository internStaffingRepository;

    @GetMapping
    public List<InternStaffingSummaryResponse> getAll() {
        log.info("Fetch all intern Staffings");
        List<InternStaffingSummaryResponse> result = internStaffingService.findAll();
        log.info("Returning all intern Staffings");

        return result;
    }

    @GetMapping("/{id}")
    public Optional<InternStaffingResponse> getById(@PathVariable Long id) {
        log.info("Fetch intern Staffing with id: {}", id);
        Optional<InternStaffingResponse> result = internStaffingService.findById(id);
        log.info("Returning intern Staffing with id: {}", id);

        return result;
    }
}