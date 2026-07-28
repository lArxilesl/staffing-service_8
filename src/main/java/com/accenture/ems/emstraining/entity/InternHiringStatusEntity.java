package com.accenture.ems.emstraining.entity;
// This class represents the entity for intern hiring status
// Tells Spring/JPA how the Java class maps to the database table intern_hiring_status

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//to reduce boilerplate code
@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "intern_hiring_status")
public class InternHiringStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status", nullable = false, length = 45)
    private String status;
}
