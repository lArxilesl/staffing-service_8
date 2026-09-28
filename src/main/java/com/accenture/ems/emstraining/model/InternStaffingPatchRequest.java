package com.accenture.ems.emstraining.model;

import lombok.Data;

@Data
public class InternStaffingPatchRequest {

    private Long employeeId;
    private Long internHiringStatusId;
    private Long internshipWorkload;
    private Long workload;
    private String extension;
}
