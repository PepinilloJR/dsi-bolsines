package com.bolsines.adapter;

public interface IAdaptadorDatosLocalización {
    
    public void adaptarDatosLocalizacion();

    public Object[] recibirDatosLocalizacion (Long nroBolsin, String codigoCM);

    public void solicitarDatosLocalizacion();
}
