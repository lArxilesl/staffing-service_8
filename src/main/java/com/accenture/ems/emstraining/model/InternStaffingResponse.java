package com.accenture.ems.emstraining.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InternStaffingResponse {

    private Long id;
    private EmployeeResponse employee;
    private InternHiringStatusResponse internHiringStatus;
    private Long internshipWorkload;
    private Long workload;
    private LocalDateTime extension;
}