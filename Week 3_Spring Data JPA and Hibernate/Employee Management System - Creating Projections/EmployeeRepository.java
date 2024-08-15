package com.example.employeemanagementsystem.repository;

import com.example.employeemanagementsystem.entity.Employee;
import com.example.employeemanagementsystem.projection.EmployeeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Fetch all employees with the projection
    List<EmployeeProjection> findAllProjectedBy();

    // Fetch employees by department name using the projection
    @Query("SELECT e.name AS name, e.email AS email, e.department.name AS departmentName FROM Employee e WHERE e.department.name = :departmentName")
    List<EmployeeProjection> findByDepartmentNameProjected(String departmentName);
}
