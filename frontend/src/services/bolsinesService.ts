import { apiClient } from "../api/client";
import type { Bolsin } from "../types/Bolsin";
import type { IniciarSeguimientoResponse } from "../types/ApiResponse";

export type ListarBolsinesParams = {
  comision?: string;
  soloDestino?: boolean;
};

export const bolsinesService = {
  listar: async (params?: ListarBolsinesParams): Promise<Bolsin[]> => {
    // const { data } = await apiClient.get<Bolsin[]>("/bolsines", { params });
    const { data } = await apiClient.get<IniciarSeguimientoResponse>("/bolsines/seguimiento/iniciar", { params });
    
    // 1. Indexamos la matriz `mapa` por numeroBolsin (como string) para búsqueda O(1)
    const mapaPorBolsin = new Map<string, [number, number]>();
    if (Array.isArray(data.mapa)) {
      for (const fila of data.mapa) {
        const [numeroBolsin, latitudGoogle, longitudGoogle] = fila;
        mapaPorBolsin.set(String(numeroBolsin), [latitudGoogle, longitudGoogle]);
      }
    }

    // 2. Mapeamos `data.bolsines` pisando las coordenadas si Google devolvió ajuste
    return data.bolsines.map((bolsin) => {
      const coordsGoogle = mapaPorBolsin.get(String(bolsin.id ?? bolsin.numeroBolsin));
      if (!coordsGoogle) return bolsin;

      return {
        ...bolsin,
        latitud: coordsGoogle[0],
        longitud: coordsGoogle[1],
      };
    });

    // return data.bolsines;
  },

  obtener: async (id: string): Promise<Bolsin> => {
    // const { data } = await apiClient.get<Bolsin>(`/bolsines/${id}`);
    const { data } = await apiClient.get<Bolsin>(`/bolsines/seguimiento/${id}`);
    return data;
  },
};