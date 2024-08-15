package com.example.employeemanagementsystem.repository;

import com.example.employeemanagementsystem.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // Custom query to find departments by partial name match
    @Query("SELECT d FROM Department d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :namePart, '%'))")
    List<Department> findDepartmentsByNamePart(@Param("namePart") String namePart);

    // Custom query to find departments with more than a specific number of employees
    @Query("SELECT d FROM Department d WHERE SIZE(d.employees) > :size")
    List<Department> findDepartmentsWithMoreThanXEmployees(@Param("size") int size);
}
