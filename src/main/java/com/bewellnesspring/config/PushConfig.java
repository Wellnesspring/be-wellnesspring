package com.bewellnesspring.config;

import nl.martijndwars.webpush.PushService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.*;

@Configuration
public class PushConfig {

    @Value("${vapid.public}")
    private String vapidPublicKey;

    @Value("${vapid.private}")
    private String vapidPrivateKey;

    @Value("${vapid.subject}")
    private String subject;


    @Bean
    public PushService pushService() {
        try {
            // PushService 생성자에 VAPID 키와 subject 전달
            return new PushService(vapidPublicKey, vapidPrivateKey, subject);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static {
        Security.addProvider(new BouncyCastleProvider());
    }
}
