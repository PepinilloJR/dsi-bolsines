import { apiClient } from "../api/client";
import type { ComisionMedica } from "../types/ComisionMedica";
import type { IniciarSeguimientoResponse } from "../types/ApiResponse";

export const comisionesService = {
  obtener: async (codigo: string): Promise<ComisionMedica> => {
    console.log("fingimos que llamamos al codigo: ", codigo)
    const { data } = await apiClient.get<IniciarSeguimientoResponse>(
      `/bolsines/seguimiento/iniciar`
    );
    // /comisiones/{codigo}
    return data.comision;
  },
};