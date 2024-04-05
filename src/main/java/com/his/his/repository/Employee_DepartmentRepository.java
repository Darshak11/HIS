package com.his.his.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.his.his.models.Department;
import com.his.his.models.Employee_Department;
import com.his.his.models.User;
import com.his.his.models.CompositePrimaryKeys.Employee_DepartmentId;

public interface Employee_DepartmentRepository extends JpaRepository<Employee_Department, Employee_DepartmentId>{

    List<Employee_Department> findEmployeesByDepartment(Department department);

    List<Employee_Department> findDepartmentsByEmployee(User employee);
}
