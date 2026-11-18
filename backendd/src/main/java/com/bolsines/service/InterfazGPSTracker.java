package com.bolsines.service;

import com.bolsines.adapter.impl.AdaptadorDatosXTR;
import com.bolsines.adapter.IAdaptadorDatosLocalización;
import lombok.Data;

// import java.util.Arrays;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

@Data
@Component 
public class InterfazGPSTracker {
    // el orden de las APIKEYS importa para su deteccion, agregar al ultimo toda apikey nueva
    private String[] apiKeys = {
        "sk_xtr_9F3a7C21bE6d4A10f2c8e5b7d913", // XTR-4500L
        "sk_nav_7b61d9e4c3a2485fae10c77d5b2f", // NavTrack QX-7A
        "sk_geo_f2d84a1c9e7345b8a6d1c3f0972e" // GeoPulse MTR-900
    };
   // private Object[][] bolsinLocation;
   // private int nroBolsin;
   // private String codigoCMO;
  //  private String codigoCMD;

    public Object[] enviarDatosLocalizacion(String apiKey, Long nroBolsin, String codigoCM) {
        IAdaptadorDatosLocalización adaptadorDatosLocalización = crearAdaptador(apiKey);
        return adaptadorDatosLocalización.recibirDatosLocalizacion(nroBolsin, codigoCM);
    }
    
    private IAdaptadorDatosLocalización crearAdaptador(String apiKey) {
        int pos = IntStream.range(0, apiKeys.length)
                .filter(key -> apiKey.equals(apiKeys[key])).findFirst()
                .orElse(-1);

        // que mierda veo, se que lo hicieron por seguir el diagrama, pero que mierda me hacen leer 
        // nonono que mierda tengo que leer por que tenemos que usar unos string como listas
        if (pos == 0) {
            return new AdaptadorDatosXTR(apiKey);
        }
        else if (pos == 1) {
            // NavTrack QX-7A
            return null;
        } else if (pos == 2) {
            // GeoPulse MTR-900
            return null;
        } else {
            return null;
        }
    }
}
