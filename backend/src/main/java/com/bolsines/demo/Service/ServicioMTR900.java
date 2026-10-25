package com.bolsines.demo.Service;

import org.springframework.web.client.RestClient;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServicioMTR900 {

    public static Object[][] fetchCargoPositions(String claveApi, int nroBolsin) {
        RestClient restClient = RestClient.builder().baseUrl("http://localhost:8080")
                .defaultHeader("X-API-Key", claveApi).build();

        String result = restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/geopulse-mtr-900/fetchCargoPositions")
                        .queryParam("numeroBolsin", nroBolsin)
                        .build())
                .retrieve().body(String.class);

        System.out.println(result);
        throw new UnsupportedOperationException();
    };
}