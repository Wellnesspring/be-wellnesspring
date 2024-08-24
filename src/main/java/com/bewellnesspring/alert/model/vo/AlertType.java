package com.bewellnesspring.alert.model.vo;

import lombok.Data;

@Data
public class AlertType {

    private Long id;
    private String alType;
    private String message1;
    private String message2;

    public AlertType(Long id, String alType, String message1, String message2) {
        this.id = id;
        this.alType = alType;
        this.message1 = message1;
        this.message2 = message2;
    }
}
