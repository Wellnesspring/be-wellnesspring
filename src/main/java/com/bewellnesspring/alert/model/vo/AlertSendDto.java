package com.bewellnesspring.alert.model.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class AlertSendDto {
    private Long id;
    private Long typeId;
    private String userId;
    private String read_or_not;
    private LocalDateTime alertTime;
    private int scheduled;
    private AlertType alertType;

    public AlertSendDto(Long id, Long typeId, String userId, String read_or_not, LocalDateTime alertTime, int scheduled, AlertType alertType) {
        this.id = id;
        this.typeId = typeId;
        this.userId = userId;
        this.read_or_not = read_or_not;
        this.alertTime = alertTime;
        this.scheduled = scheduled;
        this.alertType = alertType;
    }
}
