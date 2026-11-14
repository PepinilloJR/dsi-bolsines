import type { Bolsin } from "../types/Bolsin";
import type { ComisionMedica } from "../types/ComisionMedica";

// Centro de Córdoba Capital
const CORDOBA_CENTER = { lat: -31.4201, lng: -64.1888 };
const RADIUS_KM = 10;

// Generar coordenadas aleatorias dentro de un radio (fórmula simple)
function randomPointAround(
  center: { lat: number; lng: number },
  radiusKm: number
) {
  const r = radiusKm / 111; // 1 grado ≈ 111km
  const u = Math.random();
  const v = Math.random();
  const w = r * Math.sqrt(u);
  const t = 2 * Math.PI * v;
  const lat = center.lat + w * Math.cos(t);
  const lng =
    center.lng + (w * Math.sin(t)) / Math.cos(center.lat * (Math.PI / 180));
  return { lat, lng };
}

function makeBolsin(
  id: string,
  numero: string,
  precinto: string,
  peso: number,
  fecha: string,
  codigoOrigen: string,
  codigoDestino: string
): Bolsin {
  const p = randomPointAround(CORDOBA_CENTER, RADIUS_KM);
  return {
    id,
    fecha,
    numeroBolsin: numero,
    numeroPrecinto: precinto,
    peso,
    latitud: p.lat,
    longitud: p.lng,
    fechaHoraUltimaActualizacion: fecha,
    codigoComisionMedicaOrigen: codigoOrigen,
    codigoComisionMedicaDestino: codigoDestino,
  };
}

export const seedBolsines: Bolsin[] = [
  makeBolsin("1", "B-001", "P-998877", 15.5, "2023-10-25T10:30:00Z", "CM-0042", "CM-0107"),
  makeBolsin("2", "B-002", "P-998878", 12.0, "2023-10-25T11:15:00Z", "CM-0042", "CM-0107"),
  makeBolsin("3", "B-003", "P-998879", 18.2, "2023-10-26T09:00:00Z", "CM-0107", "CM-0042"),
  makeBolsin("4", "B-004", "P-998880", 10.8, "2023-10-26T14:45:00Z", "CM-0107", "CM-0042"),
];

export const seedComisiones: ComisionMedica[] = [
  {
    codigo: "CM-0042",
    direccion: "Av. Rivadavia 4521, CABA",
    email: "contacto@comision42.com.ar",
    nombre: "Comisión Médica N° 42",
    telefono: "+54 11 4567-8900",
    imageUrl: null,
  },
  {
    codigo: "CM-0107",
    direccion: "Calle 50 1234, La Plata",
    email: "info@comision107.com.ar",
    nombre: "Comisión Médica N° 107",
    telefono: "+54 221 400-1100",
    imageUrl:
      "https://images.unsplash.com/photo-1519494026892-80bbd2d6fd0d?w=600&q=80",
  },
];