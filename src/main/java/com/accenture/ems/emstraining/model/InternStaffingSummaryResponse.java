package com.accenture.ems.emstraining.model;

import lombok.Data;

@Data
public class InternStaffingSummaryResponse {
    private Long id;
    private Long employeeId;
    private String employeeSurname;
    private String internHiringStatus;
    private Long internshipWorkload;
    private Long workload;
    private String extension;
}