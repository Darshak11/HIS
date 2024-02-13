package com.his.his.services;

import com.his.his.models.Employee;
import com.his.his.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl {
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

    public Employee saverUser(Employee employee) {
        return employeeRepository.save(employee);
    }

    public ResponseEntity<?> signup(RegisterDto registerDto) {
        // Check if a user with the same username already exists
        if (userRepository.findByUsername(registerDto.getUsername()).isPresent()) {
            // Throw an exception or return null
            logger.error("Username already exists");
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPhone(registerDto.getPhone());
        user.setUserType(registerDto.getUserType());

        // Encode the user's password
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        Role role = roleRepository.findByRoleName(RoleName.USER);
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);
        // Save the user to the database
        String token = jwtUtilities.generateToken(registerDto.getEmail(),Collections.singletonList(role.getRoleName()));
        return new ResponseEntity<>(new BearerToken(token , "Bearer "),HttpStatus.OK);

    }


    public String login(LoginDto loginDto) {
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
        );
        // Set the authenticated user in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<String> rolesNames = new ArrayList<>();
        user.getRoles().forEach(r-> rolesNames.add(r.getRoleName()));
        String token = jwtUtilities.generateToken(user.getUsername(),rolesNames);
        return token;
        // Return the authenticated user
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

}
