package com.bolsines.demo.Service;

import org.springframework.web.client.RestClient;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServicioNavTrackQX7A {

    public static String retrieveTrackingData(String claveApi, int nroBolsin, String codigoCMD) {
        RestClient restClient= RestClient.builder().baseUrl("http://localhost:8080").
        defaultHeader("X-API-Key", claveApi).build();

        String result = restClient.get().uri(uriBuilder -> uriBuilder.path("/api/navtrack-qx-7a/retrieveTrackingData").queryParam("numeroBolsin", nroBolsin)
                        .queryParam("codigoComisionMedicaDestino", codigoCMD)
                        .build()).retrieve().body(String.class);
        
        System.out.println(result);
        throw new UnsupportedOperationException();
    };
}
