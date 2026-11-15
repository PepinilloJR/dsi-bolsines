package com.bolsines.client;

/* 
import java.util.Optional;
import com.bolsines.dto.external.XtrBolsinLocationItemDto;
import com.bolsines.dto.external.XtrBolsinLocationResponseDto; */
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

// en application .yml viven estos datos 
// gps.api.url=http://localhost:8081
/// gps.api.key=tu-api-key-aqui

// hace falta quitarlo para que no quiera arrancar con esto 
// si como el diagrama lo crea en cada vuelta, lo creamos en cada vuelta, se entiende
// si a vos te piden malas practicas haces malas practicas no le das tanta vuelta
// @Component
public class ServicioXRT4500L {

    // Necesario para utilizar en las peticiones
    // se podría inyectar con values pero bueno quien sabe que quieren de solucion por esta cosa rara que hacemos
    private static final String BASE_URL = "http://localhost:8081";

    private final RestClient restClient;
    private final String defaultApiKey;

    public ServicioXRT4500L(String apiKey) {
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

    /* hay que seguir la pelotudes de retornar un string ??¿ 
    public Optional<XtrBolsinLocationItemDto> getBolsinLocation(
            String apiKey,
            Long numeroBolsin,
            String comisionMedica
    ) {
        String keyToSend = (apiKey != null && !apiKey.isBlank()) ? apiKey : this.defaultApiKey;

        XtrBolsinLocationResponseDto response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/xtr-4500l/getBolsinLocation")
                        .queryParam("numeroBolsin", numeroBolsin.intValue())
                        .queryParam("codigoComisionMedicaOrigen", comisionMedica)
                        .build())
                .header("X-API-Key", keyToSend)
                .retrieve()
                .body(XtrBolsinLocationResponseDto.class);

        if (response == null || response.items() == null || response.items().isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(response.items().getFirst());
    }
         */
}