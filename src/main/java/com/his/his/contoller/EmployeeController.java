package com.his.his.contoller;

import com.his.his.dto.EmployeeRegisterDto;
import com.his.his.dto.EmployeeRequestDto;
import com.his.his.models.User;
import com.his.his.services.EmployeeService;

import lombok.NonNull;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody EmployeeRegisterDto registerDto) {
        return employeeService.signup(registerDto);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public List<EmployeeRequestDto> getAllEmployees() {
        return employeeService.getAllEmployees();
    }
    
    // //BUILD CREATE EMPLOYEE REST API
    // @PostMapping
    // public Employee createEmployee(@RequestBody Employee employee){
    //     // logger.debug("Employee added");
    //     return employeeRepository.save(employee);
    // }

    //BUILD GET EMPLOYEE BY ID REST API
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<EmployeeRequestDto> getEmployeeId(@PathVariable @NonNull UUID id){
        EmployeeRequestDto employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    //BUILD UPDATE EMPLOYEE REST API
    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    //post mapping vs put mapping. post used to create a resource and put used to update a resource 
    public ResponseEntity<EmployeeRequestDto> updateEmployee(@PathVariable UUID id,@RequestBody User employeeDetails){
        EmployeeRequestDto updatedEmployee=employeeService.updateEmployee(id, employeeDetails);
        return ResponseEntity.ok(updatedEmployee);
    }

    //BUILD DELETE EMPLOYEE REST API
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable UUID id){
        return employeeService.deleteEmployee(id); 
    }
}
