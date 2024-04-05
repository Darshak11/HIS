package com.his.his;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.his.his.dto.PatientRegisterDto;
import com.his.his.models.Department;
import com.his.his.models.Patient.Gender;
import com.his.his.models.Patient.PatientType;
import com.his.his.models.Role;
import com.his.his.models.User;
import com.his.his.models.User.EmployeeType;
import com.his.his.repository.UserRepository;
import com.his.his.services.DepartmentService;
import com.his.his.services.EmployeeService;
import com.his.his.services.PatientService;

import jakarta.annotation.PostConstruct;

@Component
public class DatabaseLoader {
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PatientService patientService;

    // @Autowired
    // private EmployeeService employeeService;

    @Autowired
    private UserRepository employeeRepository;

    @PostConstruct
    public void initDatabase() {
        System.out.println("Loading data into database");
        createDepartments();

        createPatients();
        createEmployees();

    }

    public void createDepartments() {
        departmentService.createDepartment(new Department("Cardiology", "Dr. John Doe", 5, 10));
        departmentService.createDepartment(new Department("Neurology", "Dr. Jane Doe", 3, 6));
        departmentService.createDepartment(new Department("Oncology", "Dr. James Doe", 4, 8));
        departmentService.createDepartment(new Department("Dermatology", "Dr. Sarah Lee", 2, 4));
        departmentService.createDepartment(new Department("Orthopedics", "Dr. William Brown", 7, 12));
        departmentService.createDepartment(new Department("Gastroenterology", "Dr. Emily Garcia", 6, 9));
        departmentService.createDepartment(new Department("Pulmonology", "Dr. David Miller", 5, 8));
        departmentService.createDepartment(new Department("Nephrology", "Dr. Olivia Jones", 3, 5));

    }

    public void createPatients() {
        patientService.signup(new PatientRegisterDto("John Doe", "12345678901234", "AADHAR123", "test@gmail.com",
                "12/12/2014", "7878787879", Gender.FEMALE, PatientType.OUTPATIENT, 12));

        patientService.signup(new PatientRegisterDto("Alice Smith", "23456789012345", "AADHAR124", "alice@gmail.com",
                "01/01/1990", "9999999999", Gender.FEMALE, PatientType.OUTPATIENT, 30));

        patientService.signup(new PatientRegisterDto("Bob Johnson", "34567890123456", "AADHAR125", "bob@gmail.com",
                "02/02/1985", "8888888888", Gender.MALE, PatientType.INPATIENT, 35));

        patientService
                .signup(new PatientRegisterDto("Charlie Brown", "45678901234567", "AADHAR126", "charlie@gmail.com",
                        "03/03/1995", "7777777777", Gender.MALE, PatientType.OUTPATIENT, 25));

        patientService.signup(new PatientRegisterDto("Diana Jones", "56789012345678", "AADHAR127", "diana@gmail.com",
                "04/04/2000", "6666666666", Gender.FEMALE, PatientType.INPATIENT, 20));

        patientService.signup(new PatientRegisterDto("Ethan Williams", "67890123456789", "AADHAR128", "ethan@gmail.com",
                "05/05/1982", "5555555555", Gender.MALE, PatientType.OUTPATIENT, 40));

        patientService.signup(new PatientRegisterDto("Fiona Garcia", "78901234567890", "AADHAR129", "fiona@gmail.com",
                "06/06/1976", "4444444444", Gender.FEMALE, PatientType.INPATIENT, 45));

        patientService.signup(new PatientRegisterDto("Grace Martinez", "89012345678901", "AADHAR130", "grace@gmail.com",
                "07/07/1998", "3333333333", Gender.FEMALE, PatientType.OUTPATIENT, 28));

        patientService.signup(new PatientRegisterDto("Henry Lee", "90123456789012", "AADHAR131", "henry@gmail.com",
                "08/08/1989", "2222222222", Gender.MALE, PatientType.INPATIENT, 33));

        patientService
                .signup(new PatientRegisterDto("Isabella Clark", "01234567890123", "AADHAR132", "isabella@gmail.com",
                        "09/09/1992", "1111111111", Gender.FEMALE, PatientType.OUTPATIENT, 36));
        patientService.signup(new PatientRegisterDto("Jack Turner", "12345678901234", "AADHAR133", "jack@gmail.com",
                "10/10/1980", "9999999999", Gender.MALE, PatientType.INPATIENT, 44));

        patientService.signup(new PatientRegisterDto("Lily Parker", "23456789012345", "AADHAR134", "lily@gmail.com",
                "11/11/1975", "8888888888", Gender.FEMALE, PatientType.OUTPATIENT, 49));

        patientService.signup(new PatientRegisterDto("Mason Adams", "34567890123456", "AADHAR135", "mason@gmail.com",
                "12/12/1993", "7777777777", Gender.MALE, PatientType.INPATIENT, 31));

        patientService.signup(new PatientRegisterDto("Natalie Hill", "45678901234567", "AADHAR136", "natalie@gmail.com",
                "01/01/2001", "6666666666", Gender.FEMALE, PatientType.OUTPATIENT, 23));

        patientService.signup(new PatientRegisterDto("Oliver Scott", "56789012345678", "AADHAR137", "oliver@gmail.com",
                "02/02/1987", "5555555555", Gender.MALE, PatientType.INPATIENT, 37));

        patientService.signup(new PatientRegisterDto(
                "William Davis",
                "678901234567890",
                "AADHAR138",
                "william@gmail.com",
                "03/03/2002",
                "4444444444",
                Gender.MALE,
                PatientType.OUTPATIENT,
                22));

        patientService.signup(new PatientRegisterDto(
                "Sophia Hernandez",
                "789012345678901",
                "AADHAR139",
                "sophia@gmail.com",
                "04/04/1999",
                "3333333333",
                Gender.FEMALE,
                PatientType.INPATIENT,
                25));

    }

    public void createEmployees() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User employee1 = new User();
        employee1.setDateOfBirth("12/05/12");
        employee1.setName("Karan");
        employee1.setLastCheckIn("1:02");
        employee1.setEmployeeStatus(User.EmployeeStatus.CHECKED_IN);
        employee1.setRole(Role.DOCTOR);
        employee1.setPassword(encoder.encode("1234"));
        employee1.setEmployeeType(EmployeeType.DOCTOR);

        User employee2 = new User();
        employee2.setDateOfBirth("15/07/15");
        employee2.setName("John");
        employee2.setLastCheckIn("2:30");
        employee2.setEmployeeStatus(User.EmployeeStatus.CHECKED_OUT);
        employee2.setRole(Role.NURSE);
        employee2.setPassword(encoder.encode("5678"));
        employee2.setEmployeeType(EmployeeType.NURSE);

        User employee3 = new User();
        employee3.setDateOfBirth("20/08/20");
        employee3.setName("Alice");
        employee3.setLastCheckIn("3:45");
        employee3.setEmployeeStatus(User.EmployeeStatus.CHECKED_IN);
        employee3.setRole(Role.ADMIN);
        employee3.setPassword(encoder.encode("9012"));
        employee3.setEmployeeType(EmployeeType.HEAD_NURSE);

        User employee4 = new User();
        employee4.setDateOfBirth("25/09/25");
        employee4.setName("Bob");
        employee4.setLastCheckIn("4:00");
        employee4.setEmployeeStatus(User.EmployeeStatus.CHECKED_OUT);
        employee4.setRole(Role.DOCTOR);
        employee4.setPassword(encoder.encode("3456"));
        employee4.setEmployeeType(EmployeeType.DOCTOR);

        User employee5 = new User();
        employee5.setDateOfBirth("30/10/30");
        employee5.setName("Charlie");
        employee5.setLastCheckIn("5:15");
        employee5.setEmployeeStatus(User.EmployeeStatus.CHECKED_IN);
        employee5.setRole(Role.NURSE);
        employee5.setPassword(encoder.encode("7890"));
        employee5.setEmployeeType(EmployeeType.NURSE);

        User employee6 = new User();
        employee6.setDateOfBirth("05/11/35");
        employee6.setName("Diana");
        employee6.setLastCheckIn("6:30");
        employee6.setEmployeeStatus(User.EmployeeStatus.CHECKED_OUT);
        employee6.setRole(Role.ADMIN);
        employee6.setPassword(encoder.encode("1234"));
        employee6.setEmployeeType(EmployeeType.ADMIN);

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);
        employeeRepository.save(employee4);
        employeeRepository.save(employee5);
        employeeRepository.save(employee6);
    }
}
