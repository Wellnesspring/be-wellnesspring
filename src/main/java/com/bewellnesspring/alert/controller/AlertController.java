package com.bewellnesspring.alert.controller;

import com.bewellnesspring.alert.model.repository.AlertMapper;
import com.bewellnesspring.alert.model.repository.SubscribeMapper;
import com.bewellnesspring.alert.model.vo.Alert;
import com.bewellnesspring.alert.model.vo.Subscribe;
import com.bewellnesspring.alert.service.AlertService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import nl.martijndwars.webpush.Subscription;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alert")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AlertController {

    private final AlertService alertService;
    private final SubscribeMapper subscribeMapper;
    private final AlertMapper alertMapper;


//    @PostMapping("/create")
//    public ResponseEntity<?> createAlert(@RequestBody AlertCreateRequest request) {
//
//        try {
//            alertService.createAlert(request.userId, request.alType, request.alertTime, request.scheduled); //알림 생성 후 db에 저장
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//
//        return ResponseEntity.status(HttpStatus.OK).body("알림이 생성되었습니다.");
//    }

    @Data
    static class AlertCreateRequest {
        private String userId;
        private String alType;
        private Date alertTime;
        private int scheduled;
    }

//    @PostMapping("/create/exercise")
//    public ResponseEntity<?> exerciseAlert(@RequestBody AlertCreateRequest request) {
//
//        try {
//            alertService.createAlert(request.userId, request.alType, request.alertTime, request.scheduled); //알림 생성 후 db에 저장
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//        return ResponseEntity.status(HttpStatus.OK).body("운동 알림이 생성되었습니다.");
//    }

    @Value("${vapid.public}")
    private String vapidPublicKey;

    @GetMapping("/web-push/public-key")
    public String getVapidPublicKey() {
        return vapidPublicKey;
    }

    @PostMapping("/save-subscription")
    public ResponseEntity<String> saveSubscription(@RequestBody Subscription sub,
                                                   @RequestBody Map<String, Long> request) {

        System.out.println("Subscription received: " + sub);

        String userId = "yoohwanjoo@nate.com";
        Subscribe subscribe = new Subscribe(userId, sub.endpoint, sub.keys.p256dh, sub.keys.auth);

        subscribeMapper.saveSubscribe(subscribe);

        return ResponseEntity.ok("{\"message\": \"Subscription saved successfully\"}");

    }
    @GetMapping("/show")
    public ResponseEntity<?> getUserAlert(@RequestParam("userId") String userId) {

        List<Alert> alerts = alertMapper.findAlertById(userId);
        if (alerts != null && !alerts.isEmpty()) {
            return ResponseEntity.ok(alerts);
        } else  {
            return ResponseEntity.badRequest().body("알람이없습니다.");
        }
    }

    @PostMapping("/read")
    public ResponseEntity<String> readAlert(@RequestBody Map<String, Long> request) {
        Long alertId = request.get("id");
        if (alertId == null) {
            return ResponseEntity.badRequest().body("해당 알람을 찾지 못했습니다.");
        }

        alertMapper.updateRead(alertId);
        return ResponseEntity.status(HttpStatus.OK).body("알람 읽음표시로 변경완료");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAlert(@RequestBody Map<String, Long> request) {
        Long alertId = request.get("id");
        if (alertId == null) {
            return ResponseEntity.badRequest().body("삭제할 알람이 없습니다.");
        }
        alertMapper.deleteAlert(alertId);
        return ResponseEntity.status(HttpStatus.OK).body("알람 삭제 성공");
    }
}





