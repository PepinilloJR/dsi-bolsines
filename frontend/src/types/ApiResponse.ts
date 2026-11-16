import type { Bolsin } from './Bolsin';
import type { ComisionMedica } from './ComisionMedica';

/**
 * Tupla que representa una fila de la matriz `mapa` devuelta por InterfazServicioGoogleMaps:
 * [0] numeroBolsin
 * [1] latitud (ajustada por Google Roads API)
 * [2] longitud (ajustada por Google Roads API)
 * [3] fechaHoraUltimaActualizacion
 * [4] numeroPrecinto
 * [5] codigoComisionMedicaDestino
 */
export type MapaFila = [
  numeroBolsin: number,
  latitud: number,
  longitud: number,
  fechaHoraUltimaActualizacion: string,
  numeroPrecinto: number,
  codigoCMDestino: string
];

export interface IniciarSeguimientoResponse {
  comision: ComisionMedica;
  bolsines: Bolsin[];
  mapa: MapaFila[];
}