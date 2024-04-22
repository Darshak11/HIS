package com.his.his.logging;

import java.sql.Timestamp;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults (level = AccessLevel.PRIVATE)
public class LogRequestDto {
    private Long id;
    private Timestamp eventDate;
    private String level;
    private String msg;
    private Exception throwable;
    private String actorId;
    private String userId;
}
