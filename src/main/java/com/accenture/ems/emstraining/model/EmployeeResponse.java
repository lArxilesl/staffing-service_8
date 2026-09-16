package com.accenture.ems.emstraining.model;

import lombok.Data;

@Data
public class EmployeeResponse {

    private Long employeeId;
    private String name;
    private String surname;
    private String startDate;
    private String endDate;
}