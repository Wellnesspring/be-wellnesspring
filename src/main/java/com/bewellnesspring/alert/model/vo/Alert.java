package com.bewellnesspring.alert.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Alert {

    private Long id;
    private Long typeId;
    private String userId;
    private String readOrNot;
    private LocalDateTime created_at;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime alertTime;
    private Integer scheduled;
    private AlertType alertType;
    private String status;

    public Alert
            (Long id, Long typeId, String userId, String readOrNot, LocalDateTime created_at, LocalDateTime alertTime, Integer scheduled, String status) {
        this.id = id;
        this.typeId = typeId;
        this.userId = userId;
        this.readOrNot = readOrNot;
        this.created_at = created_at;
        this.alertTime = alertTime;
        this.scheduled = scheduled;
        this.status = status;
    }

    public Alert(Long id, Long typeId, String userId, String readOrNot, LocalDateTime alertTime, Integer scheduled, AlertType alertType) {
        this.id = id;
        this.typeId = typeId;
        this.userId = userId;
        this.readOrNot = readOrNot;
        this.alertTime = alertTime;
        this.scheduled = scheduled;
        this.alertType = alertType;
    }

    public Alert(Long id, Long typeId, String userId, String readOrNot, LocalDateTime alertTime, Integer scheduled,  String status,String alType, String message1, String message2) {
        this.id = id;
        this.typeId = typeId;
        this.userId = userId;
        this.readOrNot = readOrNot;
        this.alertTime = alertTime;
        this.scheduled = scheduled;
        this.status = status;
        this.alertType = new AlertType(null, alType, message1, message2);
    }
}
