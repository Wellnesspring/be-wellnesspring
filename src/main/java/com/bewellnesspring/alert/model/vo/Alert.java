package com.bewellnesspring.alert.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Alert {

    private Long id;
    private Long typeId;
    private String userId;
    private String read_or_not;
    private Date created_at;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private Date alertTime;
    private int scheduled;

    public Alert
            (Long id, Long typeId, String userId, String read_or_not, Date created_at, Date alertTime, int scheduled) {
        this.id = id;
        this.typeId = typeId;
        this.userId = userId;
        this.read_or_not = read_or_not;
        this.created_at = created_at;
        this.alertTime = alertTime;
        this.scheduled = scheduled;
    }
}
