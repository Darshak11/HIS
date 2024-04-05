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

    public EmployeeController(EmployeeService employeeService) {
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
    // // logger.debug("Employee added");
    // return employeeRepository.save(employee);
    // }

    // BUILD GET EMPLOYEE BY ID REST API
    @GetMapping("{id}")
    // @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<Object> getEmployeeId(@PathVariable @NonNull UUID id) {
        try {
            EmployeeRequestDto employee = employeeService.getEmployeeById(id);
            return ResponseEntity.ok(employee);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Employee not found with id = " + id.toString(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllDoctors")
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('desk:read') or hasAuthority('headNurse:read')")
    public List<EmployeeRequestDto> getAllDoctors() {
        return employeeService.getAllDoctors();
    }

    @GetMapping("/getAllNurses")
    @PreAuthorize("hasAuthority('admin:read')")
    public List<EmployeeRequestDto> getAllNurses() {
        return employeeService.getAllNurses();
    }

    @GetMapping("/getAllHeadNurses")
    @PreAuthorize("hasAuthority('admin:read')")
    public List<EmployeeRequestDto> getAllHeadNurses() {
        return employeeService.getAllHeadNurses();
    }

    // BUILD UPDATE EMPLOYEE REST API
    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    // post mapping vs put mapping. post used to create a resource and put used to
    // update a resource
    public ResponseEntity<Object> updateEmployee(@PathVariable UUID id, @RequestBody User employeeDetails) {
        try {
            EmployeeRequestDto updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
            return ResponseEntity.ok(updatedEmployee);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Employee not found with id = " + id.toString(), HttpStatus.NOT_FOUND);
        }
    }

    // BUILD DELETE EMPLOYEE REST API
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<?> deleteEmployee(@PathVariable UUID id) {
        try {
            return employeeService.deleteEmployee(id);
        } catch (Exception e) {
            System.out.println(e.toString());
            return new ResponseEntity<>("Failed to delete employee with id = " + id.toString(), HttpStatus.NOT_FOUND);
        }
    }
}
