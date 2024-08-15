package com.example.employeemanagementsystem.repository;

import com.example.employeemanagementsystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Find employees by their department name
    List<Employee> findByDepartmentName(String departmentName);

    // Find employees whose name contains a specific keyword (case insensitive)
    List<Employee> findByNameContainingIgnoreCase(String keyword);

    // Find employees by email domain (e.g., all employees with email ending in '@example.com')
    List<Employee> findByEmailEndingWith(String domain);
}
