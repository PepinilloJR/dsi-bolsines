// src/types/Bolsin.ts
export interface Bolsin {
  id: string;
  fecha: string;
  numeroBolsin: string;
  numeroPrecinto: string;
  peso: number;
  latitud: number;
  longitud: number;
  fechaHoraUltimaActualizacion: string; // ISO 8601
  codigoComisionMedicaOrigen: string;
  codigoComisionMedicaDestino: string;
}