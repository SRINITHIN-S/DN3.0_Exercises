package com.example.employeemanagementsystem.repository;

import com.example.employeemanagementsystem.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Find all employees with pagination
    Page<Employee> findAll(Pageable pageable);

    // Find employees by department name with pagination
    Page<Employee> findByDepartmentName(String departmentName, Pageable pageable);
}
