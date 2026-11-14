import { apiClient } from "../api/client";
import type { ComisionMedica } from "../types/ComisionMedica";

export const comisionesService = {
  obtener: async (codigo: string): Promise<ComisionMedica> => {
    const { data } = await apiClient.get<ComisionMedica>(
      `/comisiones/${codigo}`
    );
    return data;
  },
};