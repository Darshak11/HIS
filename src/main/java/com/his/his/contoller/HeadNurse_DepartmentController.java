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
import com.his.his.services.PublicPrivateService;

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

    @Autowired
    private PublicPrivateService publicPrivateService;

    @GetMapping("/getDepartmentByHeadNurseID/{headNurseId}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<?> getDepartmentByHeadNurseID(@PathVariable String headNurseId) {
        try {
            UUID privateHeadNurseId = publicPrivateService.privateIdByPublicId(headNurseId);
            return ResponseEntity.ok(departmentService.convertToDepartmentRequestDto(headNurse_DepartmentService.getDepartmentByHeadNurseID(privateHeadNurseId)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("HeadNurse not found with id = "+headNurseId);
        }
    }

    @GetMapping("/getHeadNurseByDepartmentID/{departmentId}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<?> getHeadNurseByDepartmentID(@PathVariable String departmentId){
        try {
            UUID privateDepartmentId = publicPrivateService.privateIdByPublicId(departmentId);
            return ResponseEntity.ok(employeeService.convertEmployeeToDto(headNurse_DepartmentService.getHeadNurseByDepartmentID(privateDepartmentId)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Department not found with id = "+departmentId);
        }
    }
}
