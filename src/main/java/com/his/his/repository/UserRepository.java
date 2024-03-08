package com.his.his.repository;

import com.his.his.models.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,UUID> {
    Optional<User> findByEmployeeId(UUID employeeId);

    List<User> findByName(String employeeName);
}

