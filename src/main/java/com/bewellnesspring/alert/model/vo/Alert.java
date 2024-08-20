package com.bewellnesspring.alert.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class Alert {

    private Long id;
    private Long type_id;
    private Long user_id;
    private String message;
    private String read_or_not;
    private Date created_at;

    public Alert(Long id, Long type_id, Long user_id, String message, String read_or_not, Date created_at) {
        this.id = id;
        this.type_id = type_id;
        this.user_id = user_id;
        this.message = message;
        this.read_or_not = read_or_not;
        this.created_at = created_at;
    }

}
