package com.his.his.contoller;

import com.his.his.dto.EmployeeRegisterDto;
import com.his.his.dto.EmployeeRequestDto;
import com.his.his.logging.LogService;
import com.his.his.models.User;
import com.his.his.services.EmployeeService;
import com.his.his.services.PublicPrivateService;

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

    @Autowired
    private PublicPrivateService publicPrivateIdService;

    @Autowired
    private LogService loggingService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody EmployeeRegisterDto registerDto) {
        return employeeService.signup(registerDto);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public List<EmployeeRequestDto> getAllEmployees() {
        loggingService.addLog("INFO","Get all employees",null);
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
    public ResponseEntity<Object> getEmployeeId(@PathVariable @NonNull String id) {
        UUID privateEmployeeId =null;
        try {
            privateEmployeeId = publicPrivateIdService.privateIdByPublicId(id);
            EmployeeRequestDto employee = employeeService.getEmployeeById(privateEmployeeId);
            loggingService.addLog("INFO","Get Employee by Id",privateEmployeeId);
            return ResponseEntity.ok(employee);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            loggingService.addLog("ERROR", e.getMessage(), privateEmployeeId);
            return new ResponseEntity<>("Employee not found with id = " + id, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllDoctors")
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('desk:read') or hasAuthority('headNurse:read') or hasAuthority('nurse:read')")
    public List<EmployeeRequestDto> getAllDoctors() {
        loggingService.addLog("INFO","Get all doctors",null);
        return employeeService.getAllDoctors();
    }

    @GetMapping("/getAllNurses")
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('headNurse:read')")
    public List<EmployeeRequestDto> getAllNurses() {
        loggingService.addLog("INFO","Get all nurses",null);
        return employeeService.getAllNurses();
    }

    @GetMapping("/getAllHeadNurses")
    @PreAuthorize("hasAuthority('admin:read')")
    public List<EmployeeRequestDto> getAllHeadNurses() {
        loggingService.addLog("INFO","Get all head nurses",null);
        return employeeService.getAllHeadNurses();
    }

    // BUILD UPDATE EMPLOYEE REST API
    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    // post mapping vs put mapping. post used to create a resource and put used to
    // update a resource
    public ResponseEntity<Object> updateEmployee(@PathVariable String id, @RequestBody User employeeDetails) {
        UUID privateEmployeeId = null;
        try {
            privateEmployeeId = publicPrivateIdService.privateIdByPublicId(id);
            EmployeeRequestDto updatedEmployee = employeeService.updateEmployee(privateEmployeeId, employeeDetails);
            loggingService.addLog("INFO","Update Employee",privateEmployeeId);
            return ResponseEntity.ok(updatedEmployee);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            loggingService.addLog("ERROR", e.getMessage(), privateEmployeeId);
            return new ResponseEntity<>("Employee not found with id = " + id.toString(), HttpStatus.NOT_FOUND);
        }
    }

    // BUILD DELETE EMPLOYEE REST API
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<?> deleteEmployee(@PathVariable String id) {
        UUID privateEmployeeId = null;
        try {
            privateEmployeeId = publicPrivateIdService.privateIdByPublicId(id);
            loggingService.addLog("INFO","Delete Employee",privateEmployeeId);
            return employeeService.deleteEmployee(privateEmployeeId);
        } catch (Exception e) {
            System.out.println(e.toString());
            loggingService.addLog("ERROR", e.getMessage(), privateEmployeeId);
            return new ResponseEntity<>("Failed to delete employee with id = " + id.toString(), HttpStatus.NOT_FOUND);
        }
    }
}
