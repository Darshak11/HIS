package com.his.his.contoller;

import com.his.his.dto.DepartmentRequestDto;
import com.his.his.logging.LogService;
import com.his.his.models.Department;
import com.his.his.services.DepartmentService;
import com.his.his.services.PublicPrivateService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PublicPrivateService publicPrivateService;

    @Autowired
    private LogService loggingService;

    @GetMapping
    @PreAuthorize("hasAuthority('department:read')" or "hasAuthority('desk:read)")
    public List<DepartmentRequestDto> getAllDepartment() {
        loggingService.addLog("INFO", "Get all departments", null);
        return departmentService.getAllDepartments();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('department:update')")
    public DepartmentRequestDto createDepartment(@RequestBody Department department) {
        DepartmentRequestDto department1 = departmentService.createDepartment(department);
        loggingService.addLog("INFO", "Department created", department.getDepartmentId());
        return department1;
    }

    @GetMapping("{publicId}")
    @PreAuthorize("hasAuthority('department:read')")
    public ResponseEntity<?> getDepartmentId(@PathVariable String publicId) {
        UUID privateId = null;
        try {
            privateId = publicPrivateService.privateIdByPublicId(publicId);
            DepartmentRequestDto dto = departmentService.findById(privateId);
            loggingService.addLog("INFO", "Get department by id", privateId);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.err.println(e);
            if (privateId != null) {
                loggingService.addLog("ERROR", e.toString(), privateId);
            } else {
                loggingService.addLog("ERROR", e.toString(), privateId);
            }
            return new ResponseEntity<>("Department not found with id = " + publicId, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{publicId}")
    @PreAuthorize("hasAuthority('department:update')")
    public ResponseEntity<?> updateDepartment(@PathVariable String publicId, @RequestBody Department departmentDetails) {
        UUID privateId =null;
        try {
            privateId = publicPrivateService.privateIdByPublicId(publicId);
            DepartmentRequestDto updatedDto = departmentService.updateDepartment(privateId, departmentDetails);
            loggingService.addLog("INFO", "Update department", privateId);
            return ResponseEntity.ok(updatedDto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (privateId != null) {
                loggingService.addLog("ERROR", e.toString(), privateId);
            } else {
                loggingService.addLog("ERROR", e.toString(), privateId);
            }
            return new ResponseEntity<>("Department not found with id = " + publicId.toString(), HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("{publicId}")
    @PreAuthorize("hasAuthority('department:delete')")
    public ResponseEntity<?> deleteDepartment(@PathVariable String publicId) {
        UUID privateId =null;
        try {
            privateId = publicPrivateService.privateIdByPublicId(publicId);
            ResponseEntity<HttpStatus> httpStatus = departmentService.deleteDepartment(privateId);
            loggingService.addLog("INFO", "Delete Department", privateId);
            return httpStatus;
        } catch (Throwable e) {
            System.out.println("ERROR: ___" + e.getMessage());
            if (privateId != null) {
                System.err.println(e.getMessage());
                loggingService.addLog("ERROR", e.getMessage().substring(0, Math.min(e.getMessage().length(), 200)), privateId);
            } else {
                loggingService.addLog("ERROR", e.getMessage(), privateId);
            }
            return new ResponseEntity<>("Department cannot be deleted with id = " + publicId.toString(), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/getDepartmentId")
    @PreAuthorize("hasAuthority('department:read')")
    public ResponseEntity<?> getDepartmentIdByName(@RequestParam String departmentName) {
        try {
            List<UUID> privateIds = departmentService.getDepartmentIdByName(departmentName);
            List<String> publicIds = privateIds.stream()
                    .map(privateId -> publicPrivateService.publicIdByPrivateId(privateId))
                    .collect(Collectors.toList());
                    loggingService.addLog("INFO", "Get department id by Name", null);
            return ResponseEntity.ok(publicIds);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            loggingService.addLog("ERROR", e.getMessage(), null);
            return new ResponseEntity<>("Department not found with name = " + departmentName, HttpStatus.NOT_FOUND);
        }

    }

}
