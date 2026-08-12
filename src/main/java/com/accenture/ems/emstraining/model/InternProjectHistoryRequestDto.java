package com.accenture.ems.emstraining.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InternProjectHistoryRequestDto {

    @NotNull
    private Long internStaffingId;

    @NotNull
    private Long projectId;

    @NotNull
    private Long responsiblePersonId;

    @NotNull
    private Long projectHiringStatusId;

    @NotNull
    @Size(max = 200)
    private String comments;
}
