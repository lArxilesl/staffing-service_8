package com.accenture.ems.emstraining.repository;

import com.accenture.ems.emstraining.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {}
