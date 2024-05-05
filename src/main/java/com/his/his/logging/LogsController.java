package com.his.his.logging;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.his.his.services.PublicPrivateService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@CrossOrigin("*")
@RequestMapping("/logs")
public class LogsController {
    @Autowired
    private LogService logService;

    @Autowired
    private PublicPrivateService publicPrivateService;

    @GetMapping("/getLogsByActorId")
    public ResponseEntity<?>getLogsByActorId(){
        try{
            return ResponseEntity.ok(logService.getLogsByActorId());
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getLogsByUserId/{userId}")
    public ResponseEntity<?>getLogsByUserId(@PathVariable String userId){
        try{

            return ResponseEntity.ok(logService.getLogsByUserId(userId));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/getAllLogs")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<?>getAllLogs(){
        try{
            return ResponseEntity.ok(logService.getAllLogs());
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getLogsByActorandUserId/{actorId}/{userId}")
    public ResponseEntity<?>getLogsByActorandUserId(@PathVariable String actorId, @PathVariable String userId){
        UUID actorIdUUID = null;
        UUID userIdUUID = null;
        try{
            actorIdUUID = publicPrivateService.privateIdByPublicId(actorId);
            userIdUUID = publicPrivateService.privateIdByPublicId(userId);
            return ResponseEntity.ok(logService.getLogsByActorandUserId(actorIdUUID, userIdUUID));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getLogsByDepartment/{employeeId}")
    @PreAuthorize("hasAuthority('headNurse:read')")
    public ResponseEntity<?>getLogsByDepartment(@PathVariable String employeeId){
        UUID employeeIdUUID = null;
        try{
            employeeIdUUID = publicPrivateService.privateIdByPublicId(employeeId);
            return ResponseEntity.ok(logService.getLogsByDepartment(employeeIdUUID));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
