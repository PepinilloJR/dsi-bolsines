package com.bolsines.demo.App;

import lombok.Data;

@Data
public class InterfazGPSTracker {
    private String[] apiKeys = {
        "sk_xtr_9F3a7C21bE6d4A10f2c8e5b7d913",
        "sk_nav_7b61d9e4c3a2485fae10c77d5b2f",
        "sk_geo_f2d84a1c9e7345b8a6d1c3f0972e"
    };

    private Object[][] bolsinLocation;

    private int nroBolsin;

    private String codigoCMO;

    private String codigoCMD;

    public Object[][] enviarDatosLocalizacion() {
        return null;
    }

}
