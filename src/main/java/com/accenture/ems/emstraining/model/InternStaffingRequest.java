package com.accenture.ems.emstraining.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class InternStaffingRequest {

    @NotNull
    private Long employeeId;

    @NotNull
    private Long internHiringStatusId;

    private Long internshipWorkload;
    private Long workload;

    @NotBlank
    private String extension;
}