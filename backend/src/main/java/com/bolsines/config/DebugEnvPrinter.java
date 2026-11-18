package com.bolsines.config;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DebugEnvPrinter {

    @Value("${gps.base-url}")
    private String gpsBaseUrl;

    @PostConstruct
    public void init() {
        System.out.println("==================================================");
        System.out.println("VALOR ACTUAL DE GPS_BASE_URL:" + gpsBaseUrl);
        System.out.println("==================================================");
    }
}