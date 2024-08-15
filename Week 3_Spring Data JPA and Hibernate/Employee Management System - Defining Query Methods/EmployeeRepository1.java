package com.example.employeemanagementsystem.repository;

import com.example.employeemanagementsystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Using NamedQuery defined in the Employee entity
    List<Employee> findByDepartmentName(@Param("departmentName") String departmentName);

    // Using NamedQuery to find employees by email domain
    List<Employee> findByEmailDomain(@Param("domain") String domain);
}
