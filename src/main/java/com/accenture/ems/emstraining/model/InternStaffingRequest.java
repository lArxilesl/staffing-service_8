package com.accenture.ems.emstraining.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class InternStaffingRequest {

    @NotNull
    private Long employeeId;

    @NotNull
    private Long internHiringStatusId;

    private Long internshipWorkload;
    private Long workload;

    @NotNull(message = "Extension cannot be null")
    private LocalDateTime extension;
}