package com.his.his.logging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@CrossOrigin("*")
@RequestMapping("/logs")
public class LogsController {
    @Autowired
    private LogService logService;

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
}
