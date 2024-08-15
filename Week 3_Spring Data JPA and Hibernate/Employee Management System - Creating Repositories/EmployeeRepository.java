package com.example.employeemanagementsystem.repository;

import com.example.employeemanagementsystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Derived query method to find an employee by email
    Optional<Employee> findByEmail(String email);

    // Derived query method to find employees by department ID
    List<Employee> findByDepartmentId(Long departmentId);

    // Derived query method to find employees by name (case-insensitive)
    List<Employee> findByNameIgnoreCase(String name);
}
