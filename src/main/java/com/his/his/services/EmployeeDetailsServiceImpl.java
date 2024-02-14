package com.his.his.services;

import com.his.his.dto.EmployeeRegisterDto;
import com.his.his.dto.LoginDto;
import com.his.his.models.Employee;
import com.his.his.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeDetailsServiceImpl {
    @Autowired
    private final EmployeeRepository employeeRepository;

    @Autowired
    private final JwtUtilities jwtUtilities;

    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final AuthenticationManager authenticationManager;

    public EmployeeDetailsServiceImpl(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

//    public Employee saveUser(Employee employee) {
//        return employeeRepository.save(employee);
//    }

    public ResponseEntity<?> signup(EmployeeRegisterDto registerDto) {
        // Check if a user with the same username already exists
        if (userRepository.findByUsername(registerDto.getUsername()).isPresent()) {
            // Throw an exception or return null
            throw new RuntimeException("Username already exists");
        }

        Employee employee = new Employee();
        employee.setUsername(registerDto.getUsername());
        employee.setDateOfBirth(registerDto.getDob());
        employee.setLastCheckIn(registerDto.getLastCheckIn());
        employee.setStatus(registerDto.getStatus());
        employee.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        //Write code to add role to the employee -

//        Role role = roleRepository.findByRoleName(RoleName.USER);
//        employee.setRoles(Collections.singletonList(role));

        //Save the employee
        employeeRepository.save(employee);

        //Return bearer token
        String token = jwtUtilities.generateToken(registerDto.getUsername(),Collections.singletonList(role.getRoleName()));
        return new ResponseEntity<>(new BearerToken(token , "Bearer "),HttpStatus.OK);
    }


    public String login(LoginDto loginDto) {
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
        );

        // Set the authenticated user in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Employee employee = employeeRepository.findByUsername(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<String> rolesNames = new ArrayList<>();
        user.getRoles().forEach(r-> rolesNames.add(r.getRoleName()));
        return jwtUtilities.generateToken(employee.getUsername(),rolesNames);
    }

//    public User findByUsername(String username) {
//        return userRepository.findByUsername(username).orElse(null);
//    }
//
//    public User findById(Long id) {
//        return userRepository.findById(id).orElse(null);
//    }

}
