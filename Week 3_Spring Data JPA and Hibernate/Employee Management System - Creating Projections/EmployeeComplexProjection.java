package com.example.employeemanagementsystem.projection;

import org.springframework.beans.factory.annotation.Value;

public interface EmployeeComplexProjection {

    String getName();

    String getEmail();

    @Value("#{target.name + ' (' + target.department.name + ')'}")
    String getEmployeeWithDepartment();
}
