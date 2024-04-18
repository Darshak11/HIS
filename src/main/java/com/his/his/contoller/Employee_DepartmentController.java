package com.his.his.contoller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.his.his.dto.EmployeeRequestDto;
import com.his.his.services.Employee_DepartmentService;
import com.his.his.services.PublicPrivateService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@CrossOrigin("*")
@RequestMapping("/employeeDepartment")
public class Employee_DepartmentController {
    @Autowired
    private Employee_DepartmentService employeeDepartmentService;

    @Autowired
    private PublicPrivateService publicPrivateService;

    @GetMapping("/getAllEmployeesByDepartmentID/{departmentId}")
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('headNurse:read')")
    public ResponseEntity<?> getAllEmployeesByDepartmentID(@PathVariable String departmentId) {
        try {
            UUID privateDepartmentId = publicPrivateService.privateIdByPublicId(departmentId);
            List<EmployeeRequestDto> employees = employeeDepartmentService.getAllEmployeesByDepartmentID(privateDepartmentId);
        if(employees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        else
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Department not found with id = "+departmentId.toString(), HttpStatus.NOT_FOUND);
        }
        
    }

    @GetMapping("/getDepartmentByEmployeeID/{employeeId}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<?> getDepartmentByEmployeeID(@PathVariable String employeeId) {
        try {
            UUID privateEmployeeId = publicPrivateService.privateIdByPublicId(employeeId);
            return employeeDepartmentService.getDepartmentByEmployeeID(privateEmployeeId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Employee not found with id = "+employeeId.toString(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllNursesByDepartmentID/{departmentId}")
    @PreAuthorize("hasAuthority('nurse:read') or hasAuthority('headNurse:read')")
    public ResponseEntity<?> getAllNursesByDepartmentID(@PathVariable String departmentId) {
        try {
            UUID privateDepartmentId = publicPrivateService.privateIdByPublicId(departmentId);
            List<EmployeeRequestDto> nurses = employeeDepartmentService.getAllNursesByDepartmentID(privateDepartmentId);
            if(nurses.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            else
                return ResponseEntity.ok(nurses);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Department not found with id = "+departmentId.toString(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllDoctorsByDepartmentID/{departmentId}")
    @PreAuthorize("hasAuthority('doctor:read') or hasAuthority('headNurse:read') or hasAuthority('nurse:read')")
    public ResponseEntity<?> getAllDoctorsByDepartmentID(@PathVariable String departmentId) {
        try {
            UUID privateDepartmentId = publicPrivateService.privateIdByPublicId(departmentId);
            List<EmployeeRequestDto> doctors = employeeDepartmentService.getAllDoctorsByDepartmentID(privateDepartmentId);
            if(doctors.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            else
                return ResponseEntity.ok(doctors);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Department not found with id = "+departmentId, HttpStatus.NOT_FOUND);
        }
    }
}
