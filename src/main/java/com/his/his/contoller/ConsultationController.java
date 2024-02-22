package com.his.his.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.his.his.models.Consultation;
import com.his.his.models.Patient;
import com.his.his.repository.ConsultationRepository;
import com.his.his.repository.PatientRepository;
import com.his.his.services.ConsultationService;

@RestController
@CrossOrigin("*")
@RequestMapping("/consultation")
public class ConsultationController {
    @Autowired
    private ConsultationService consultationService;

    public ConsultationController(ConsultationService consultationService){
        this.consultationService=consultationService;
    }

    @PostMapping
    public ResponseEntity<?>addConsultation(@RequestBody Consultation consultation){
        return consultationService.addConsultation(consultation);
    }

    @Autowired
    private ConsultationRepository consultationRepository;

    @GetMapping
    public List<Consultation> getAllConsultations(){
        return consultationRepository.findAll();
    }
}
