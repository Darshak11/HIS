package com.his.his.contoller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.his.his.services.DepartmentService;
import com.his.his.services.EmployeeService;
import com.his.his.services.HeadNurse_DepartmentService;

@RestController
@CrossOrigin("*")
@RequestMapping("/headNurseDepartment")
public class HeadNurse_DepartmentController {
    
    @Autowired
    private HeadNurse_DepartmentService headNurse_DepartmentService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/getDepartmentByHeadNurseID/{headNurseId}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<?> getDepartmentByHeadNurseID(@PathVariable UUID headNurseId) {
        try {
            return ResponseEntity.ok(departmentService.convertToDepartmentRequestDto(headNurse_DepartmentService.getDepartmentByHeadNurseID(headNurseId)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("HeadNurse not found with id = "+headNurseId.toString());
        }
    }

    @GetMapping("/getHeadNurseByDepartmentID/{departmentId}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<?> getHeadNurseByDepartmentID(@PathVariable UUID departmentId){
        try {
            return ResponseEntity.ok(employeeService.convertEmployeeToDto(headNurse_DepartmentService.getHeadNurseByDepartmentID(departmentId)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Department not found with id = "+departmentId.toString());
        }
    }
}
