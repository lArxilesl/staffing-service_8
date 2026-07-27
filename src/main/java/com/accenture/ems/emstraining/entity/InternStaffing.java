package com.accenture.ems.emstraining.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "intern_staffing")
public class InternStaffing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Long employeeId;

    @ManyToOne
    @JoinColumn(name = "internship_hiring_status")
    private Long internshipHiringStatusId;

    @Column(name = "internship_workload")
    private Integer internshipWorkload;

    @Column(name = "workload")
    private Integer workload;

    @Column(name = "extension")
    private LocalDateTime extension;
}
