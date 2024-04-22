package com.his.his.logging;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Logs, Long>{

    List<Logs> findByActorId(UUID actorId);

    List<Logs> findByUserId(UUID userId);
    
}
