package com.his.his;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.his.his.models.Consultation;
import com.his.his.models.Employee;
import com.his.his.models.Patient;
import com.his.his.models.Patient.Gender;
import com.his.his.models.Patient.PatientType;
import com.his.his.repository.ConsultationRepository;
import com.his.his.repository.EmployeeRepository;
import com.his.his.repository.PatientRepository;

import java.util.UUID;

@SpringBootApplication
public class HisApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(HisApplication.class, args);
	}

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private ConsultationRepository consultationRepository;

	@Override
	public void run(String ...args) throws Exception{
		Employee employee=new Employee();
		employee.setDateOfBirth("12/05/12");
		employee.setName("Karan");
		employee.setLastCheckIn("1:02");
		employee.setEmployeeStatus(Employee.EmployeeStatus.CHECKED_IN);
		Employee employee1=new Employee();
		employee1.setName("Darshak");
		employee1.setDateOfBirth("12/03/12");
		employee1.setLastCheckIn("1:02");
		employee1.setEmployeeStatus(Employee.EmployeeStatus.CHECKED_OUT);
		employeeRepository.save(employee);
		employeeRepository.save(employee1);


		Patient patient=new Patient();
		patient.setName("Karan");
		patient.setAabhaId("1234");
		patient.setAadharId("123456");
		patient.setEmailId("kadn@gmail.com");
		patient.setDateOfBirth("12/06/2002");
		patient.setEmergencyContactNumber("45454545454");
		patient.setGender(Gender.MALE);
		patient.setPatientType(PatientType.INPATIENT);

		Patient patient1=new Patient();
		patient1.setName("Darshak");
		patient1.setAabhaId("1234");
		patient1.setAadharId("123456");
		patient1.setEmailId("kadn@gmail.com");
		patient1.setDateOfBirth("12/06/2002");
		patient1.setEmergencyContactNumber("45454545454");
		patient1.setGender(Gender.MALE);
		patient1.setPatientType(PatientType.OUTPATIENT);


		patientRepository.save(patient);
		patientRepository.save(patient1);

		Consultation consultation=new Consultation();
		consultation.setPatientId(UUID.fromString("28db04a5-7424-4121-97a7-0b43581e6aff"));
		consultation.setDoctorId(UUID.fromString("d6d9094e-3141-458c-b50d-054ea2916d2c"));
		consultation.setEmrId(UUID.fromString("dd6d664f-ce61-4420-b38e-43d221e59f2d"));

		Consultation consultation1=new Consultation();
		consultation1.setPatientId(UUID.fromString("3c660473-9cc3-4e59-bb00-713a3a1a4a97"));
		consultation1.setDoctorId(UUID.fromString("3c660473-9cc3-4e59-bb00-713a3a1a4a97"));
		consultation1.setEmrId(UUID.fromString("3c660473-9cc3-4e59-bb00-713a3a1a4a97"));

		consultationRepository.save(consultation);
		consultationRepository.save(consultation1);
	}

}
