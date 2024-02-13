package com.his.his.contoller;

import com.his.his.dto.EmployeeRegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeDetailsServiceImpl userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody EmployeeRegisterDto registerDto) {
        return userService.signup(registerDto);
    }
}
