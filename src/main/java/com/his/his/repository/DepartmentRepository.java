package com.his.his.repository;

import com.his.his.models.Department;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DepartmentRepository extends JpaRepository<Department,UUID> {


    List<Department> findByDepartmentName(String departmentName);
}
