package com.his.his.notification;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationTokenRepository extends JpaRepository<NotificationToken,UUID> {

    boolean existsByToken(String token);

    NotificationToken findByToken(String token);

    void deleteDistinctByEmployeeIdAndToken(UUID employeeId, String token);

    boolean existsByEmployeeId(UUID employeeId);

    List<NotificationToken> findAllByEmployeeId(UUID employeeId);
}
