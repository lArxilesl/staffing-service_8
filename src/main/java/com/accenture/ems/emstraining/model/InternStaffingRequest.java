package com.accenture.ems.emstraining.model;

import lombok.Data;

@Data
public class InternStaffingRequest {

    private EmployeeResponse employee;
    private InternHiringStatusResponse internHiringStatus;
    private Long internshipWorkload;
    private Long workload;
    private String extension;
}