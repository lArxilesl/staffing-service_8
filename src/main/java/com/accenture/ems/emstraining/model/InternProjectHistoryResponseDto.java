package com.accenture.ems.emstraining.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class InternProjectHistoryResponseDto {
    private Long id;
    private Long internStaffingId;
    private Long projectId;
    private Long responsiblePersonId;
    private Long projectHiringStatusId;
    private String comments;
}
