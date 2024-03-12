package com.his.his.contoller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.his.his.dto.DepartmentRequestDto;
import com.his.his.dto.EmployeeRequestDto;
import com.his.his.services.Employee_DepartmentService;

@RestController
@CrossOrigin("*")
@RequestMapping("/employeeDepartment")
public class Employee_DepartmentController {
    @Autowired
    private Employee_DepartmentService employeeDepartmentService;

    @GetMapping("/getAllEmployeesByDepartmentID/{departmentId}")
    @PreAuthorize("hasAuthority('employee:read')")
    public ResponseEntity<List<EmployeeRequestDto>> getAllEmployeesByDepartmentID(@PathVariable UUID departmentId) {
        List<EmployeeRequestDto> employees = employeeDepartmentService.getAllEmployeesByDepartmentID(departmentId);
        if(employees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        else
            return ResponseEntity.ok(employees);
    }

    @GetMapping("/getDepartmentByEmployeeID/{employeeId}")
    @PreAuthorize("hasAuthority('department:read')")
    public ResponseEntity<DepartmentRequestDto> getDepartmentByEmployeeID(@PathVariable UUID employeeId) {
        return employeeDepartmentService.getDepartmentByEmployeeID(employeeId);
    }

    @GetMapping("/getAllNursesByDepartmentID/{departmentId}")
    @PreAuthorize("hasAuthority('nurse:read')")
    public ResponseEntity<List<EmployeeRequestDto>> getAllNursesByDepartmentID(@PathVariable UUID departmentId) {
        List<EmployeeRequestDto> nurses = employeeDepartmentService.getAllNursesByDepartmentID(departmentId);
        if(nurses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        else
            return ResponseEntity.ok(nurses);
    }

    @GetMapping("/getAllDoctorsByDepartmentID/{departmentId}")
    @PreAuthorize("hasAuthority('doctor:read')")
    public ResponseEntity<List<EmployeeRequestDto>> getAllDoctorsByDepartmentID(@PathVariable UUID departmentId) {
        List<EmployeeRequestDto> doctors = employeeDepartmentService.getAllDoctorsByDepartmentID(departmentId);
        if(doctors.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        else
            return ResponseEntity.ok(doctors);
    }
}
