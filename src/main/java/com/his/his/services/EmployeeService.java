package com.his.his.services;

import com.his.his.dto.EmployeeRegisterDto;
import com.his.his.models.Employee;
import com.his.his.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Service
@Transactional
// @RequiredArgsConstructor
public class EmployeeService {
    @Autowired
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public ResponseEntity<?> signup(EmployeeRegisterDto registerDto) {
        //Create patient object from DTO
        Employee employee =new Employee();
        employee.setName(registerDto.getName());
        employee.setDateOfBirth(registerDto.getDob());
        // employee.setEmployeeStatus(registerDto.getEmployeeStatus());
        // employee.setLastCheckIn(registerDto.getLastCheckIn());
        

        //Put this in Exception block for handling failure
        employeeRepository.save(employee);

        return new ResponseEntity<>("Patient Registered Successfully",HttpStatus.OK);
    }


}
