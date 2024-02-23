package com.his.his.contoller;

import com.his.his.dto.EmployeeRegisterDto;
import com.his.his.exception.ResourceNotFoundException;
import com.his.his.models.Employee;
import com.his.his.repository.EmployeeRepository;
import com.his.his.services.EmployeeService;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    // //BUILD CREATE EMPLOYEE REST API
    // @PostMapping
    // public Employee createEmployee(@RequestBody Employee employee){
    //     // logger.debug("Employee added");
    //     return employeeRepository.save(employee);
    // }

    //BUILD GET EMPLOYEE BY ID REST API
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeId(@PathVariable UUID id){
        Employee employee=employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee not exist with id "+id)) ;
        // logger.debug("Employee found");
        return ResponseEntity.ok(employee);
    }

    //BUILD UPDATE EMPLOYEE REST API
    @PutMapping("{id}")
    //post mapping vs put mapping. post used to create a resource and put used to update a resource 
    public ResponseEntity<Employee> updateEmployee(@PathVariable UUID id,@RequestBody Employee employeeDetails){
        Employee updateEmployee = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee does not exist with id "+id));
        updateEmployee.setName(employeeDetails.getName());
        updateEmployee.setDateOfBirth(employeeDetails.getDateOfBirth());
        updateEmployee.setEmployeeStatus(employeeDetails.getEmployeeStatus());
        employeeRepository.save(updateEmployee);
        // logger.debug("Successfully updated the employee");
        return ResponseEntity.ok(updateEmployee);
    }

    //BUILD DELETE EMPLOYEE REST API
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable UUID id){
        Employee employee = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee not exists "+id));

        employeeRepository.delete(employee);
        // logger.debug("Delete employee");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
