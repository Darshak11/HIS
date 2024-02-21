package com.his.his.repository;

import com.his.his.models.Employee;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,UUID> {
    
}

