package com.his.his.contoller;

import com.his.his.dto.EmployeeRegisterDto;
import com.his.his.services.EmployeeDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeDetailsServiceImpl employeeService;
    public EmployeeController(EmployeeDetailsServiceImpl employeeService){
        this.employeeService = employeeService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody EmployeeRegisterDto registerDto) {
        return employeeService.signup(registerDto);
    }
}
