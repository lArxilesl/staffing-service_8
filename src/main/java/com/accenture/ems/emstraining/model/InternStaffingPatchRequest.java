package com.accenture.ems.emstraining.model;

import lombok.Data;

import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Data
public class InternStaffingPatchRequest {

    private Long employeeId;
    private Long internHiringStatusId;

    @PositiveOrZero(message = "internshipWorkload cannot be negative")
    private Long internshipWorkload;

    @PositiveOrZero(message = "workload cannot be negative")
    private Long workload;

    private LocalDateTime extension;

    public boolean isEmpty() {
        return employeeId == null
                && internHiringStatusId == null
                && internshipWorkload == null
                && workload == null
                && extension == null;
    }
}
