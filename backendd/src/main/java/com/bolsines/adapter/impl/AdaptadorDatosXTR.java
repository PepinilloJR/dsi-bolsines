package com.bolsines.adapter.impl;


import com.bolsines.adapter.IAdaptadorDatosLocalización;
import com.bolsines.client.ServicioXRT4500L;
import com.bolsines.dto.external.XtrBolsinLocationItemDto;
import com.bolsines.dto.external.XtrBolsinLocationResponseDto;


import tools.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDateTime;

/* import org.springframework.beans.factory.annotation.Value;
import lombok.*;
import java.time.DateTimeException;
import java.util.Arrays;
import java.util.Optional;
import com.bolsines.model.Bolsin; */

// @RequiredArgsConstructor
public class AdaptadorDatosXTR implements IAdaptadorDatosLocalización {

    // Agregación del UML
    private final ServicioXRT4500L servicioXRT4500L; 

    // agregado por seguir la boludes de que devuelva un string el cliente
    private final ObjectMapper objectMapper;

    // @Value("${gps.api.key}")
    private String apiKey;

    private Long nroBolsin;
    private String codigoCMO;
    private String datosLocalizacionRecibidos;
    private Object[] datosLocalizacionAdaptados;

    // Constructor acorde al UML: + new(claveApi: String) + referencia al cliente HTTP
    public AdaptadorDatosXTR(String claveApi) {
        this.apiKey = claveApi;
        this.servicioXRT4500L = new ServicioXRT4500L(claveApi);
        this.objectMapper = new ObjectMapper();
    }

    public String getApiKey() {
        return this.apiKey;
    }

    // metodo polimorfico?¿ o como se diga
    public Object[] recibirDatosLocalizacion(Long nroBolsin, String codigoCM) {
        this.nroBolsin = nroBolsin;
        this.codigoCMO = codigoCM;

        // comunicacion con el client externo
        solicitarDatosLocalizacion();

        // metodo del adapter (?????????????????????????????????????)
        adaptarDatosLocalizacion();

        return datosLocalizacionAdaptados;
    };

    public void solicitarDatosLocalizacion() {
        this.datosLocalizacionRecibidos = servicioXRT4500L.getBolsinLocation(
                this.apiKey,
                this.nroBolsin.intValue(),
                this.codigoCMO
        );
    }

    public void adaptarDatosLocalizacion() {
        if (this.datosLocalizacionRecibidos == null || this.datosLocalizacionRecibidos.isBlank()) {
            this.datosLocalizacionAdaptados = new Object[0];
            return;
        }

        try {
            // mapea a la entrada como List<DTO>
            XtrBolsinLocationResponseDto dto = objectMapper.readValue(
                    this.datosLocalizacionRecibidos,
                    XtrBolsinLocationResponseDto.class
            );

            if (dto == null || dto.items() == null || dto.items().isEmpty()) {
                this.datosLocalizacionAdaptados = new Object[0];
                return;
            }

            // como el endpoint solo recibe uno a la vez devolvemos solo el primero
            XtrBolsinLocationItemDto item = dto.items().getFirst();

            // 
            LocalDateTime fechaAdaptada = this.adaptarFechaHora(item.fechaHoraUltimaActualizacion());

            this.datosLocalizacionAdaptados = new Object[]{
                    item.numeroBolsin(),
                    item.latitud(),
                    item.longitud(),
                    fechaAdaptada
            };
        } catch (Exception e) {
            throw new RuntimeException("Error adaptando JSON de XTR-4500L", e);
        }
    }

    public LocalDateTime adaptarFechaHora(String fechaIso) {
        if (fechaIso == null || fechaIso.isBlank()) {
            return null;
        }
        return LocalDateTime.parse(fechaIso);
    }
}
