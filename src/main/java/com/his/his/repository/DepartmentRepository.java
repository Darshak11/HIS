package com.his.his.repository;

import com.his.his.models.Department;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DepartmentRepository extends JpaRepository<Department,UUID> {


    Optional<Department> findByDepartmentName(String departmentName);
}
