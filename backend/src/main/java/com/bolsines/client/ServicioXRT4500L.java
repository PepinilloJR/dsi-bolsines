package com.bolsines.client;

import org.springframework.web.client.RestClient;

// en application .yml viven estos datos 
// gps.api.url=http://localhost:8081
/// gps.api.key=tu-api-key-aqui

public class ServicioXRT4500L {

    // unica forma de usar la env url por el problema de que no podemos usar component
    private static String BASE_URL = System.getenv("GPS_BASE_URL");

    private final RestClient restClient;
    private final String defaultApiKey;

    public ServicioXRT4500L(String apiKey) {
        if (BASE_URL == null || BASE_URL.isBlank()) {
            BASE_URL = "http://localhost:8081"; // Fallback por defecto cuando no se ejecuta con docker
        }
        this.restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .build();
        this.defaultApiKey = apiKey;
    }

    public String getBolsinLocation(
        String apiKey,
        int numeroBolsin,
        String codigoCMO
    ) {
        String keyToSend = (apiKey != null && !apiKey.isBlank()) ? apiKey : this.defaultApiKey;

        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/xtr-4500l/getBolsinLocation")
                        .queryParam("numeroBolsin", numeroBolsin)
                        .queryParam("codigoComisionMedicaOrigen", codigoCMO)
                        .build())
                .header("X-API-Key", keyToSend)
                .retrieve()
                .body(String.class);
    }
}