package com.example.employeemanagementsystem.repository;

import com.example.employeemanagementsystem.dto.EmployeeDTO;
import com.example.employeemanagementsystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Fetch employees with class-based projection
    @Query("SELECT new com.example.employeemanagementsystem.dto.EmployeeDTO(e.name, e.email, e.department.name) FROM Employee e")
    List<EmployeeDTO> findAllEmployeeDTOs();

    // Fetch employees by department name with class-based projection
    @Query("SELECT new com.example.employeemanagementsystem.dto.EmployeeDTO(e.name, e.email, e.department.name) FROM Employee e WHERE e.department.name = :departmentName")
    List<EmployeeDTO> findEmployeeDTOsByDepartmentName(@Param("departmentName") String departmentName);
}
