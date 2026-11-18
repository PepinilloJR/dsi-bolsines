package com.bolsines.client;


import org.springframework.web.client.RestClient;

import lombok.Data;

@Data
public class ServicioNavTrackQX7A {

    private static final String BASE_URL = "http://localhost:8081";

    private final RestClient restClient;
    private final String defaultApiKey;

    public ServicioNavTrackQX7A(String apiKey) {
        this.restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .build();
        this.defaultApiKey = apiKey;
    }

    public String getBolsinLocation(
        String apiKey,
        int numeroBolsin,
        String codigoCMD
    ) {
        String keyToSend = (apiKey != null && !apiKey.isBlank()) ? apiKey : this.defaultApiKey;

        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/navtrack-qx-7a/retrieveTrackingData")
                        .queryParam("numeroBolsin", numeroBolsin)
                        .queryParam("codigoComisionMedicaDestino", codigoCMD)    // revisar ms-
                        .build())
                .header("X-API-Key", keyToSend)
                .retrieve()
                .body(String.class);
    }
}