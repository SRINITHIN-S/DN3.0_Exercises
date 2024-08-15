package com.example.employeemanagementsystem.projection;

public interface EmployeeProjection {

    String getName();

    String getEmail();

    // You can also fetch nested properties like the department name
    String getDepartmentName();
}
