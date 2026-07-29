package com.accenture.ems.emstraining.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectHiringStatusRequest {

    @NotBlank(message = "Status is required")
    @Size(max = 45, message = "Status must be maximum 45 characters")
    private String status;
}