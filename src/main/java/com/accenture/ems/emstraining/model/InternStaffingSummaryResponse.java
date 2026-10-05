package com.accenture.ems.emstraining.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InternStaffingSummaryResponse {
    private Long id;
    private Long employeeId;
    private String fullName;
    private String internHiringStatus;
    private Integer internshipWorkload;
    private Integer workload;
    private LocalDateTime extension;
}