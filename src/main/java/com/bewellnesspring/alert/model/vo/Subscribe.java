package com.bewellnesspring.alert.model.vo;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Subscribe {

    private Long id;
    private String userId;
    private String endPoint;
    private String publicKey;
    private String authToken;
    private LocalDateTime subscribe_at;

    public Subscribe(Long id, String userId, String endPoint, String publicKey, String authToken, LocalDateTime subscribe_at) {
        this.id = id;
        this.userId = userId;
        this.endPoint = endPoint;
        this.publicKey = publicKey;
        this.authToken = authToken;
        this.subscribe_at = subscribe_at;
    }

    public Subscribe(String userId, String endPoint, String publicKey, String authToken) {
        this.userId = userId;
        this.endPoint = endPoint;
        this.publicKey = publicKey;
        this.authToken = authToken;
    }

    public Subscribe(String endPoint, String publicKey, String authToken) {
        this.endPoint = endPoint;
        this.publicKey = publicKey;
        this.authToken = authToken;
    }
}
