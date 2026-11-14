package com.bolsines.demo.App;

import com.bolsines.demo.Implementations.AdaptadorDatosXTR4500L;
import com.bolsines.demo.Interfaces.IAdaptadorDatosLocalización;
import lombok.Data;

import java.util.Arrays;
import java.util.stream.IntStream;

@Data
public class InterfazGPSTracker {
    // el orden de las APIKEYS importa para su deteccion, agregar al ultimo toda apikey nueva
    private String[] apiKeys = {
        "sk_xtr_9F3a7C21bE6d4A10f2c8e5b7d913", // XTR-4500L
        "sk_nav_7b61d9e4c3a2485fae10c77d5b2f", // NavTrack QX-7A
        "sk_geo_f2d84a1c9e7345b8a6d1c3f0972e" // GeoPulse MTR-900
    };

    private Object[][] bolsinLocation;

    private int nroBolsin;

    private String codigoCMO;

    private String codigoCMD;

    public Object[] enviarDatosLocalizacion(String APIKey, int nroBolsin, int codigoCM) {
        return null;
    }
    
    public IAdaptadorDatosLocalización crearAdaptador(String APIkey) {
        int pos = IntStream.range(0, apiKeys.length)
                .filter(key -> APIkey.equals(apiKeys[key])).findFirst()
                .orElse(-1);

        if (pos == 0) {
            return new AdaptadorDatosXTR4500L(APIkey);
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
