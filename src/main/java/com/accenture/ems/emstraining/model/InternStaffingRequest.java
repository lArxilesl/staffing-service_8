package com.accenture.ems.emstraining.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Data
public class InternStaffingRequest {

    @NotNull
    private Long employeeId;

    @NotNull
    private Long internHiringStatusId;

    @PositiveOrZero(message = "internshipWorkload cannot be negative")
    private Long internshipWorkload;
    @PositiveOrZero(message = "workload cannot be negative")
    private Long workload;

    @NotNull(message = "Extension cannot be null")
    private LocalDateTime extension;
}