package com.his.his.contoller;

import com.his.his.dto.DepartmentRequestDto;
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

    @GetMapping
    @PreAuthorize("hasAuthority('department:read')")
    public List<DepartmentRequestDto> getAllDepartment() {
        return departmentService.getAllDepartments();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('department:update')")
    public DepartmentRequestDto createDepartment(@RequestBody Department department) {
        return departmentService.createDepartment(department);
    }

    @GetMapping("{publicId}")
    @PreAuthorize("hasAuthority('department:read')")
    public ResponseEntity<?> getDepartmentId(@PathVariable String publicId) {
        try {
            UUID privateId = publicPrivateService.privateIdByPublicId(publicId);
            DepartmentRequestDto dto = departmentService.findById(privateId);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Department not found with id = " + publicId, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{publicId}")
    @PreAuthorize("hasAuthority('department:update')")
    public ResponseEntity<?> updateDepartment(@PathVariable String publicId, @RequestBody Department departmentDetails) {
        try {
            UUID privateId = publicPrivateService.privateIdByPublicId(publicId);
            DepartmentRequestDto updatedDto = departmentService.updateDepartment(privateId, departmentDetails);
            return ResponseEntity.ok(updatedDto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Department not found with id = " + publicId.toString(), HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("{publicId}")
    @PreAuthorize("hasAuthority('department:delete')")
    public ResponseEntity<?> deleteDepartment(@PathVariable String publicId) {
        try {
            UUID privateId = publicPrivateService.privateIdByPublicId(publicId);
            return departmentService.deleteDepartment(privateId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Department not found with id = " + publicId.toString(), HttpStatus.NOT_FOUND);
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
            return ResponseEntity.ok(publicIds);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Department not found with name = " + departmentName, HttpStatus.NOT_FOUND);
        }

    }

}
