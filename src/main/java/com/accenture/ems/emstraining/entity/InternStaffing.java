package com.accenture.ems.emstraining.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "intern_staffing")
public class InternStaffing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intern_hiring_status_id", nullable = false)
    private InternHiringStatus internHiringStatus;

    @Column(name = "internship_workload")
    private Integer internshipWorkload;

    @Column(name = "workload")
    private Integer workload;

    @Column(name = "extension", nullable = false)
    private LocalDateTime extension;
}