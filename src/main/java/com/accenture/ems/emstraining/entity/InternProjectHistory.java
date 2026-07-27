package com.accenture.ems.emstraining.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "intern_project_history")
public class InternProjectHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "intern_staffing_id", nullable = false)
    private Long internStaffingId;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Long projectId;

    @ManyToOne
    @JoinColumn(name = "responsible_person_id", nullable = false)
    private Long responsiblePersonId;

    @ManyToOne
    @JoinColumn(name = "project_hiring_status_id", nullable = false)
    private Long projectHiringStatusId;

    @Column(name = "comments")
    private String comments;
}
