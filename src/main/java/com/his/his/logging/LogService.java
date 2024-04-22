package com.his.his.logging;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.his.his.services.PublicPrivateService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class LogService {

    @Autowired
    PublicPrivateService publicPrivateService;

    @Autowired
    private LogRepository logRepository;

    public LogRequestDto converttoDto(Logs logs) {
        LogRequestDto dto = new LogRequestDto();
        dto.setId(logs.getId());
        dto.setEventDate(logs.getEventDate());;
        dto.setLevel(logs.getLevel());
        dto.setMsg(logs.getMsg());
        dto.setThrowable(logs.getThrowable());
        dto.setActorId(publicPrivateService.publicIdByPrivateId(logs.getActorId()));
        dto.setUserId(publicPrivateService.publicIdByPrivateId(logs.getUserId()));
        return dto;
    }

    public void addLog(String level, String msg, Exception throwable, UUID userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID actorId = UUID.fromString(authentication.getName());
        logRepository.save(new Logs(level, msg, throwable, actorId, userId));
    }

    // TODO: HOW TO DEAL WITH THROWABLE? DO WE EVEN NEED IT?

    public List<LogRequestDto> getLogsByActorId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID actorId = UUID.fromString(authentication.getName());
        return logRepository.findByActorId(actorId)
                .stream()
                .map(Logs -> {
                    LogRequestDto dto = converttoDto(Logs);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<LogRequestDto> getLogsByUserId(String user) {
        System.out.println(user);
        UUID userId = publicPrivateService.privateIdByPublicId(user);
        return logRepository.findByUserId(userId)
                .stream()
                .map(Logs -> {
                    LogRequestDto dto = converttoDto(Logs);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<LogRequestDto> getAllLogs() {
        return logRepository.findAll().stream()
                .map(Logs -> {
                    LogRequestDto dto = converttoDto(Logs);
                    return dto;
                })
                .collect(Collectors.toList());
    }

}