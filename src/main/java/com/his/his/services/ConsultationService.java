package com.his.his.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.his.his.models.Consultation;
import com.his.his.repository.ConsultationRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ConsultationService {
    private final ConsultationRepository consultationRepository;

    @Autowired
    public ConsultationService(ConsultationRepository consultationRepository){
        this.consultationRepository=consultationRepository;
    }

    public ResponseEntity<?> addConsultation(Consultation _consultation){
        
        Consultation consultation = new Consultation();
        consultation.setDoctor(_consultation.getDoctor());
        consultation.setEmrId(_consultation.getEmrId());
        consultation.setPatient(_consultation.getPatient());

        consultationRepository.save(consultation);
        return new ResponseEntity<>("Consultation Added successfully",HttpStatus.OK);
    }

    public List<Consultation> getAllConsultations(){
        return consultationRepository.findAll();
    }
}
