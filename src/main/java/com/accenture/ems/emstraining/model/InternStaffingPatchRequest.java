package com.accenture.ems.emstraining.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InternStaffingPatchRequest {

    private Long employeeId;
    private Long internHiringStatusId;
    private Long internshipWorkload;
    private Long workload;
    private LocalDateTime extension;
}
