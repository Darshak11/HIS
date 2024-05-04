package com.his.his.logging;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Logs, Long>{

    List<Logs> findByActorId(String actorId);

    List<Logs> findByUserId(String userId);

    Collection<Logs> findByActorIdAndUserId(String string, String string2);
    
}
