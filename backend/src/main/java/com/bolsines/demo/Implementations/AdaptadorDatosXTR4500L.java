package com.bolsines.demo.Implementations;

import com.bolsines.demo.Interfaces.IAdaptadorDatosLocalización;
import com.bolsines.demo.Service.ServicioXRT4500L;
import lombok.*;
import tools.jackson.databind.ObjectMapper;

import java.util.Arrays;

@RequiredArgsConstructor
public class AdaptadorDatosXTR4500L implements IAdaptadorDatosLocalización {

    final String APIKey;

    int nroBolsin;
    String codigoCMO;
    Object[] datosLocalizaciónAdaptados;
    String datosLocalizacionRecibidos;

    public Object[] recibirDatosLocalizacion(int nroBolsin, String codigoCM) {
        this.nroBolsin = nroBolsin;
        this.codigoCMO = codigoCM;

        solicitarDatosLocalización();
        adaptadorDatosLocalizacion();
    };

    public void adaptadorDatosLocalizacion() {
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



    };

    public void solicitarDatosLocalización() {
        datosLocalizacionRecibidos = ServicioXRT4500L.getBolsinLocation(APIKey, nroBolsin, codigoCMO);
    };
}
