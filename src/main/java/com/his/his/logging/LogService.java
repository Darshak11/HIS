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
        // dto.setThrowable(logs.getThrowable());
        dto.setActorId(publicPrivateService.publicIdByPrivateId(UUID.fromString(logs.getActorId())));
        dto.setUserId(publicPrivateService.publicIdByPrivateId(UUID.fromString(logs.getUserId())));
        return dto;
    }

    public void addLog(String level, String msg, UUID userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String actorId =authentication.getName();
        String userIdString = (userId != null) ? userId.toString() : "null";
        logRepository.save(new Logs(level, msg, actorId, userIdString));
    }

    // TODO: HOW TO DEAL WITH THROWABLE? DO WE EVEN NEED IT?

    public List<LogRequestDto> getLogsByActorId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String actorId = authentication.getName();
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
        String userId = publicPrivateService.privateIdByPublicId(user).toString();
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

    public List<LogRequestDto> getLogsByActorandUserId(UUID actorId, UUID userId) {
        // Logs log = new Logs();
        // log.setActorId(actorId.toString());
        // log.setUserId(userId.toString());
        // log.setLevel("APP");
        // log.setMsg("Get all doctors");
        // logRepository.save(log);
        // // log.setLevel("INFO");
        // logRepository.save(log);
        return logRepository.findByActorIdAndUserIdAndLevel(actorId.toString(), userId.toString(), "APP")
                .stream()
                .map(Logs -> {
                    LogRequestDto dto = converttoDto(Logs);
                    return dto;
                })
                .collect(Collectors.toList());
    }

}
