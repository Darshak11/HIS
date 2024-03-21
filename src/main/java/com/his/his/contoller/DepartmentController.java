package com.his.his.contoller;

import com.his.his.dto.DepartmentRequestDto;
import com.his.his.models.Department;
import com.his.his.services.DepartmentService;

import java.util.List;
import java.util.UUID;
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

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('department:read')")
    public ResponseEntity<?> getDepartmentId(@PathVariable UUID id) {
        try {
            DepartmentRequestDto dto = departmentService.findById(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Department not found with id = " + id.toString(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('department:update')")
    public ResponseEntity<?> updateDepartment(@PathVariable UUID id,
            @RequestBody Department departmentDetails) {
        try {
            DepartmentRequestDto updatedDto = departmentService.updateDepartment(id, departmentDetails);
            return ResponseEntity.ok(updatedDto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Department not found with id = " + id.toString(), HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('department:delete')")
    public ResponseEntity<?> deleteDepartment(@PathVariable UUID id) {
        try {
            return departmentService.deleteDepartment(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Department not found with id = " + id.toString(), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/getDepartmentId")
    @PreAuthorize("hasAuthority('department:read')")
    public ResponseEntity<?> getDepartmentIdByName(@RequestParam String departmentName) {
        try {
            return ResponseEntity.ok(departmentService.getDepartmentIdByName(departmentName));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Department not found with name = " + departmentName, HttpStatus.NOT_FOUND);
        }

    }

}
