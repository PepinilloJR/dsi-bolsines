package com.bolsines.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import tools.jackson.databind.JsonNode;

/**
 * Prepara los datos que necesita la librería de mapas del front para mostrar
 * la posición de los bolsines (CU 36, paso 6).
 *
 * Envía las coordenadas recibidas del GPS Tracker a Google Roads API
 * (nearestRoads) y las reemplaza por las que devuelve Google: el punto más
 * cercano sobre una calle de su mapa. El front recibe el resultado como JSON
 * y la librería (Leaflet / Google Maps) dibuja un marcador por fila.
 *
 * Si no hay API key configurada o Google no responde, se conservan las
 * coordenadas del GPS (siguen siendo representables en el mapa).
 *
 * Formato de cada fila (entrada y salida), un bolsín por fila:
 *   [0] numeroBolsin                  (Integer)  - GPS
 *   [1] latitud                       (Double)   - GPS (ajustada por Google)
 *   [2] longitud                      (Double)   - GPS (ajustada por Google)
 *   [3] fechaHoraUltimaActualizacion  (String)   - GPS
 *   [4] numeroPrecinto                (Integer)  - Bolsin (lo agrega el Gestor)
 *   [5] codigoCMDestino               (String)   - Bolsin.destino (lo agrega el Gestor)
 */
public class InterfazServicioGoogleMaps {

    public static final int nroBolsin = 0;
    public static final int latitud = 1;
    public static final int longitud = 2;
    public static final int fechaHoraUltimaActualizacion = 3;
    public static final int nroPrecinto = 4;
    public static final int codCMDestino = 5;

    private static final int cantidadColumnas = 6;

    private static final String urlRoadsApi = "https://roads.googleapis.com";

    // nearestRoads acepta hasta 100 puntos por solicitud
    private static final int maxPuntosPorSolicitud = 100;

    private final String apiKey;
    private final RestClient restClient;

    // Último mapa obtenido (atributo "- mapa" del diagrama de clases)
    private Object[][] mapa = new Object[0][];

    /**
     * @param apiKey API key de Google Maps Platform con Roads API habilitada.
     *               Si es null o vacía, no se consulta a Google.
     */
    public InterfazServicioGoogleMaps(String apiKey) {
        this(apiKey, urlRoadsApi);
    }

    /** Permite apuntar a otro servidor (por ejemplo, uno simulado para pruebas). */
    public InterfazServicioGoogleMaps(String apiKey, String urlBase) {
        this.apiKey = apiKey;
        this.restClient = RestClient.create(urlBase);
    }

    /**
     * Devuelve los bolsines que pueden representarse en el mapa, con las
     * coordenadas ajustadas por Google cuando es posible.
     * Descarta las filas sin coordenadas válidas (flujo alternativo A3:
     * el GPS Tracker no pudo informar la ubicación del bolsín).
     *
     * @param localizaciones un bolsín por fila, con el formato de la clase
     * @return un bolsín por fila, mismo formato, solo los ubicables
     */
    public Object[][] obtenerMapa(Object[][] localizaciones) {
        List<Object[]> bolsinesUbicables = new ArrayList<>();

        if (localizaciones != null) {
            for (Object[] fila : localizaciones) {
                if (esUbicable(fila)) {
                    // copia: no se modifica la matriz que mandó el Gestor
                    bolsinesUbicables.add(fila.clone());
                }
            }
        }

        if (hayApiKey()) {
            for (int desde = 0; desde < bolsinesUbicables.size(); desde += maxPuntosPorSolicitud) {
                int hasta = Math.min(desde + maxPuntosPorSolicitud, bolsinesUbicables.size());
                ajustarConGoogle(bolsinesUbicables.subList(desde, hasta));
            }
        }

        this.mapa = bolsinesUbicables.toArray(new Object[0][]);
        return this.mapa;
    }

    /**
     * Llama a nearestRoads con las coordenadas de las filas (en el mismo orden)
     * y reemplaza latitud/longitud por las que devuelve Google.
     * Las filas sin respuesta de Google conservan las coordenadas del GPS.
     */
    private void ajustarConGoogle(List<Object[]> filas) {
        // "-31.4135,-64.181|-31.3987,-64.1655" (una coordenada por fila)
        String points = filas.stream()
                .map(fila -> fila[latitud] + "," + fila[longitud])
                .collect(Collectors.joining("|"));

        try {
            JsonNode respuesta = restClient.get()
                    .uri(uri -> uri.path("/v1/nearestRoads")
                            .queryParam("points", points)
                            .queryParam("key", apiKey)
                            .build())
                    .retrieve()
                    .body(JsonNode.class);

            if (respuesta == null) {
                return;
            }

            Set<Integer> filasAjustadas = new HashSet<>();
            for (JsonNode punto : respuesta.path("snappedPoints")) {
                int indice = punto.path("originalIndex").asInt(-1);
                JsonNode ubicacion = punto.path("location");

                boolean indiceValido = indice >= 0 && indice < filas.size();
                boolean tieneUbicacion = ubicacion.has("latitude") && ubicacion.has("longitude");

                // en calles doble mano puede venir más de un punto por fila: se usa el primero
                if (indiceValido && tieneUbicacion && filasAjustadas.add(indice)) {
                    filas.get(indice)[latitud] = ubicacion.path("latitude").asDouble();
                    filas.get(indice)[longitud] = ubicacion.path("longitude").asDouble();
                }
            }
        } catch (RestClientException e) {
            // Google no respondió o devolvió error: se mantienen las coordenadas del GPS
        }
    }

    private boolean hayApiKey() {
        return apiKey != null && !apiKey.isBlank();
    }

    private boolean esUbicable(Object[] fila) {
        if (fila == null || fila.length < cantidadColumnas) {
            return false;
        }
        if (!(fila[latitud] instanceof Number lat) || !(fila[longitud] instanceof Number lng)) {
            return false;
        }
        double latitud = lat.doubleValue();
        double longitud = lng.doubleValue();
        return latitud >= -90 && latitud <= 90
                && longitud >= -180 && longitud <= 180;
    }
}