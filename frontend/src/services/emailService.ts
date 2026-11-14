import { apiClient } from "../api/client";

export type EnviarEmailBolsinPayload = {
  bolsinId: string;
  gerenteEmail: string;
  ubicacion: { lat: number; lng: number };
  numeroBolsin: string;
  numeroPrecinto: string;
};

export type EnviarEmailBolsinResponse = {
  ok: boolean;
  destinatario: string;
};

export const emailService = {
  enviarUbicacionBolsin: async (
    payload: EnviarEmailBolsinPayload
  ): Promise<EnviarEmailBolsinResponse> => {
    const { data } = await apiClient.post<EnviarEmailBolsinResponse>(
      `/bolsines/${payload.bolsinId}/email`,
      {
        gerenteEmail: payload.gerenteEmail,
        ubicacion: payload.ubicacion,
        numeroBolsin: payload.numeroBolsin,
        numeroPrecinto: payload.numeroPrecinto,
      }
    );
    return data;
  },
};