package com.his.his.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.his.his.models.PublicPrivateId;

public interface PublicPrivateRepository extends JpaRepository<PublicPrivateId, Integer>{

    PublicPrivateId findByPublicId(String publicId);

    PublicPrivateId findByPrivateId(UUID privateId);
    
}
