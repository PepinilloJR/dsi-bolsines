import { apiClient } from "../api/client";
import type { Bolsin } from "../types/Bolsin";

export type ListarBolsinesParams = {
  comision?: string;
  soloDestino?: boolean;
};

export const bolsinesService = {
  listar: async (params?: ListarBolsinesParams): Promise<Bolsin[]> => {
    const { data } = await apiClient.get<Bolsin[]>("/bolsines", { params });
    return data;
  },

  obtener: async (id: string): Promise<Bolsin> => {
    const { data } = await apiClient.get<Bolsin>(`/bolsines/${id}`);
    return data;
  },
};