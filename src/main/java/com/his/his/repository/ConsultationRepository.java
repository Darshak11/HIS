package com.his.his.repository;

import com.his.his.models.Consultation;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Consultation,UUID> {

}
