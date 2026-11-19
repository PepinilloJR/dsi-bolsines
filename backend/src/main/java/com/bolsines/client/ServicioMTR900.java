package com.bolsines.client;

import org.springframework.web.client.RestClient;

import lombok.Data;

@Data
public class ServicioMTR900 {

    private static final String BASE_URL = System.getenv("GPS_BASE_URL");

    private final RestClient restClient;
    private final String defaultApiKey;

    public ServicioMTR900(String apiKey) {
        this.restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .build();
        this.defaultApiKey = apiKey;
    }

    public Object[][] getBolsinLocation(
        String apiKey,
        int numeroBolsin
    ) {
        String keyToSend = (apiKey != null && !apiKey.isBlank()) ? apiKey : this.defaultApiKey;

        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/geopulse-mtr-900/fetchCargoPositions")
                        .queryParam("numeroBolsin", numeroBolsin)
                        .build())
                .header("X-API-Key", keyToSend)
                .retrieve()
                .body(Object[][].class);
    }
}