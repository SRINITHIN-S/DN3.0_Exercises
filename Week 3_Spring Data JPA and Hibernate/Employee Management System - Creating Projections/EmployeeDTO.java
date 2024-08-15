package com.example.employeemanagementsystem.dto;

public class EmployeeDTO {

    private final String name;
    private final String email;
    private final String departmentName;

    public EmployeeDTO(String name, String email, String departmentName) {
        this.name = name;
        this.email = email;
        this.departmentName = departmentName;
    }

    // Getters for each field
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartmentName() {
        return departmentName;
    }
}
