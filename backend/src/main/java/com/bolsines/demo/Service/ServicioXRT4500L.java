package com.bolsines.demo.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import lombok.Data;
import lombok.NoArgsConstructor;


@Service
@Data
@NoArgsConstructor
public class ServicioXRT4500L {

    public static String getBolsinLocation(String claveApi, int nroBolsin, String codigoCMO) {
        RestClient restClient= RestClient.builder().baseUrl("http://localhost:8080").
        defaultHeader("X-API-Key", claveApi).build();

        String result = restClient.get().uri(uriBuilder -> uriBuilder.path("/api/xtr-4500l/getBolsinLocation").queryParam("numeroBolsin", nroBolsin)
                        .queryParam("codigoComisionMedicaOrigen", codigoCMO)
                        .build()).retrieve().body(String.class);
        
        System.out.println(result);
        throw new UnsupportedOperationException();
    };
}
