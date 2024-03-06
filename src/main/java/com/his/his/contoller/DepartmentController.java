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
    public List<DepartmentRequestDto> getAllDepartment(){
        return departmentService.getAllDepartments();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('department:update')")
    public DepartmentRequestDto createDepartment(@RequestBody Department department){
        return departmentService.createDepartment(department);
    }


    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('department:read')")
    public ResponseEntity<DepartmentRequestDto> getDepartmentId(@PathVariable UUID id){
        DepartmentRequestDto dto = departmentService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('department:update')")
    public ResponseEntity<DepartmentRequestDto> updateDepartment(@PathVariable UUID id, @RequestBody Department departmentDetails){
        DepartmentRequestDto updatedDto = departmentService.updateDepartment(id, departmentDetails); 
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('department:delete')")
    public ResponseEntity<HttpStatus> deleteDepartment(@PathVariable UUID id){
        return departmentService.deleteDepartment(id);
    }

    @GetMapping("/getDepartmentId")
    @PreAuthorize("hasAuthority('department:read')")
    public ResponseEntity<List<UUID>> getDepartmentIdByName(@RequestParam String departmentName) {
        return ResponseEntity.ok(departmentService.getDepartmentIdByName(departmentName));
    }

    /*
        TODO
         1. Add doctor and nurse to the department.
         2. Need to create several models like doctor-department model, nurse-department etc.
         3. Need to implement list all doctors, nurses, patients in the department etc.
         4.  Create a function to get department id given a department name. Can reuse this for patient doctor etc. Since id yaad nhi rhegi bt naam yaad rhega. Department and patient ke liye krdiya baaki ka krna baaki hai

    */


}
