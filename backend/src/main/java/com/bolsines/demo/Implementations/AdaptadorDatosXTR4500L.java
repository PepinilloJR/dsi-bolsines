package com.bolsines.demo.Implementations;

import com.bolsines.demo.Domain.Bolsin;
import com.bolsines.demo.Interfaces.IAdaptadorDatosLocalización;
import com.bolsines.demo.Service.ServicioXRT4500L;
import lombok.*;
import tools.jackson.databind.ObjectMapper;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Arrays;

@RequiredArgsConstructor
public class AdaptadorDatosXTR4500L implements IAdaptadorDatosLocalización {

    final String APIKey;

    int nroBolsin;
    int codigoCMO;
    Object[] datosLocalizaciónAdaptados;
    String datosLocalizacionRecibidos;

    public Object[] recibirDatosLocalizacion(int nroBolsin, int codigoCM) {
        this.nroBolsin = nroBolsin;
        this.codigoCMO = codigoCM;

        solicitarDatosLocalización();
        adaptarDatosLocalizacion();

        return datosLocalizaciónAdaptados;
    };

    public void adaptarDatosLocalizacion() {
        ObjectMapper objectMapper = new ObjectMapper();

        // estructura que devuelve el servicio

        class Result {
            public Object[] items;
        }

        class Item {
            int numeroBolsin;
            double latitud;
            double longitud;
            String fechaHoraUltimaActualizacion;
        }

        Result result = objectMapper.readValue(datosLocalizacionRecibidos, Result.class);

        Item[] items = (Item[]) result.items;

        datosLocalizaciónAdaptados = Arrays.stream(items).map(i ->
        {
            LocalDateTime fechaHoraUltimaActualizacion = adaptarFechaHora(i.fechaHoraUltimaActualizacion);

            return new Bolsin(i.numeroBolsin, i.latitud, i.longitud, fechaHoraUltimaActualizacion);
        }).toArray();

    };

    public LocalDateTime adaptarFechaHora (String datetime) {
        return LocalDateTime.parse(datetime);
    }

    public void solicitarDatosLocalización() {
        datosLocalizacionRecibidos = ServicioXRT4500L.getBolsinLocation(APIKey, nroBolsin, codigoCMO);
    };
}
