package com.accenture.ems.emstraining.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class InternStaffingPatchRequest {

    private Long employeeId;
    private Long internHiringStatusId;
    private Long internshipWorkload;
    private Long workload;

    @NotBlank
    private String extension;
}
