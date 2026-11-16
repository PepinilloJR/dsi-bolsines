// src/screens/ComisionMedicaScreen.tsx
import { useParams, Link, useNavigate } from "react-router-dom";
import { useState, useEffect, useMemo } from "react";
import {
  MapPin,
  Mail,
  Phone,
  Building2,
  ArrowLeft,
  Package,
  CheckCircle2,
  Circle,
  Search,
  Filter,
  X,
} from "lucide-react";
import { MapContainer, TileLayer, Marker, Popup, useMap } from "react-leaflet";
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import { useComisionMedica } from "../hooks/useComisionMedica";
import { useBolsines } from "../hooks/useBolsines";
import type { Bolsin } from "../types/Bolsin";

const FALLBACK_IMAGE = "https://images.unsplash.com/photo-1519494026892-80bbd2d6fd0d?w=600&q=80";
// const FALLBACK_IMAGE = "https://i.pinimg.com/736x/3a/14/7e/3a147e7647d480fb753e684085208344.jpg";
  

// --- Fix típico de Leaflet + Vite ---
delete (L.Icon.Default.prototype as any)._getIconUrl;
L.Icon.Default.mergeOptions({
  iconRetinaUrl:
    "https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon-2x.png",
  iconUrl: "https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon.png",
  shadowUrl: "https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png",
});

const selectedIcon = new L.Icon({
  iconUrl:
    "https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-blue.png",
  shadowUrl: "https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png",
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
  shadowSize: [41, 41],
});

const defaultIcon = new L.Icon.Default();

/**
 * Vuela al bolsín seleccionado (solo hay uno a la vez).
 */
function FlyToSelected({
  bolsines,
  selectedId,
}: {
  bolsines: Bolsin[];
  selectedId: string | null;
}) {
  const map = useMap();

  useEffect(() => {
    if (!selectedId) return;
    const target = bolsines.find((b) => b.id === selectedId);
    if (target) {
      map.flyTo([target.latitud, target.longitud], 14, { duration: 0.8 });
    }
  }, [selectedId, bolsines, map]);

  return null;
}

function MapResizer() {
  const map = useMap();
  useEffect(() => {
    const t = setTimeout(() => map.invalidateSize(), 100);
    return () => clearTimeout(t);
  }, [map]);
  return null;
}

export const ComisionMedicaScreen = () => {
  const { codigo } = useParams<{ codigo: string }>();
  const { comision, isLoading: loadingComision, error: errorComision } =
    useComisionMedica(codigo);
  const { bolsines, isLoading: loadingBolsines, error: errorBolsines } =
    useBolsines();

  // ...
  const navigate = useNavigate();

  // --- Selección única ---
  const [selectedId, setSelectedId] = useState<string | null>(null);

  // --- Filtros ---
  const [filtroPrecinto, setFiltroPrecinto] = useState("");
  const [filtroCmDestino, setFiltroCmDestino] = useState<string>("");

  const handleSelect = (id: string) => {
    setSelectedId((prev) => (prev === id ? null : id));
  };

  // --- Lista de CM destino disponibles (para el <select>) ---
  const cmDestinoDisponibles = useMemo(() => {
    const set = new Set<string>();
    bolsines.forEach((b) => {
      if (b.codigoComisionMedicaDestino) set.add(b.codigoComisionMedicaDestino);
    });
    return Array.from(set).sort();
  }, [bolsines]);

  // --- Bolsines filtrados (client-side) ---
  const bolsinesFiltrados = useMemo(() => {
    return bolsines.filter((b) => {
      const matchPrecinto = filtroPrecinto
        ? b.numeroPrecinto
            .toLowerCase()
            .includes(filtroPrecinto.trim().toLowerCase())
        : true;
      const matchCmDestino = filtroCmDestino
        ? b.codigoComisionMedicaDestino === filtroCmDestino
        : true;
      return matchPrecinto && matchCmDestino;
    });
  }, [bolsines, filtroPrecinto, filtroCmDestino]);

  // --- Si el seleccionado deja de estar visible, lo deseleccionamos ---
  useEffect(() => {
    if (!selectedId) return;
    const stillVisible = bolsinesFiltrados.some((b) => b.id === selectedId);
    if (!stillVisible) setSelectedId(null);
  }, [bolsinesFiltrados, selectedId]);

  const hayFiltrosActivos = filtroPrecinto !== "" || filtroCmDestino !== "";

  const limpiarFiltros = () => {
    setFiltroPrecinto("");
    setFiltroCmDestino("");
  };

  // Centro del mapa estable
  const mapCenter = useMemo<[number, number]>(() => {
    if (bolsinesFiltrados.length > 0) {
      return [bolsinesFiltrados[0].latitud, bolsinesFiltrados[0].longitud];
    }
    if (bolsines.length > 0) {
      return [bolsines[0].latitud, bolsines[0].longitud];
    }
    return [-31.4201, -64.1888];
  }, [bolsinesFiltrados, bolsines]);

  // --- Estados de carga / error ---
  if (loadingComision || loadingBolsines) {
    return (
      <div className="max-w-6xl mx-auto p-6 text-center text-gray-500">
        Cargando datos...
      </div>
    );
  }

  if (errorComision) {
    return (
      <div className="max-w-6xl mx-auto p-6">
        <Link
          to="/"
          className="inline-flex items-center gap-2 text-sm text-gray-500 hover:text-blue-600 mb-4"
        >
          <ArrowLeft className="w-4 h-4" />
          Volver al inicio
        </Link>
        <div className="p-8 text-center text-red-500 bg-red-50 rounded-xl border border-red-100">
          {errorComision}
        </div>
      </div>
    );
  }

  if (!comision) return null;

  const imageSrc = comision.imageUrl ?? FALLBACK_IMAGE;

  return (
    <div className="max-w-6xl mx-auto p-6 space-y-8">
      <Link
        to="/"
        className="inline-flex items-center gap-2 text-sm text-gray-500 hover:text-blue-600 transition-colors"
      >
        <ArrowLeft className="w-4 h-4" />
        Volver al inicio
      </Link>

      {/* --- DATOS DE LA COMISIÓN MÉDICA --- */}
      <section className="bg-white rounded-2xl shadow-md border border-gray-100 overflow-hidden">
        <div className="grid md:grid-cols-[280px_1fr]">
          <div className="relative h-56 md:h-auto bg-gray-100">
            <img
              src={imageSrc}
              alt={comision.nombre}
              className="w-full h-full object-cover"
              onError={(e) => {
                (e.target as HTMLImageElement).src = FALLBACK_IMAGE;
              }}
            />
            <span className="absolute top-3 left-3 bg-blue-600 text-white text-xs font-semibold px-3 py-1 rounded-full shadow">
              {comision.codigo}
            </span>
          </div>

          <div className="p-6 flex flex-col justify-center space-y-4">
            <h1 className="text-2xl font-bold text-gray-900 flex items-center gap-2">
              <Building2 className="w-6 h-6 text-blue-600" />
              {comision.nombre}
            </h1>

            <ul className="space-y-3 text-gray-600">
              <li className="flex items-center gap-3">
                <MapPin className="w-5 h-5 text-gray-400 shrink-0" />
                <span>{comision.direccion}</span>
              </li>
              <li className="flex items-center gap-3">
                <Mail className="w-5 h-5 text-gray-400 shrink-0" />
                <a
                  href={`mailto:${comision.email}`}
                  className="hover:text-blue-600 transition-colors break-all"
                >
                  {comision.email}
                </a>
              </li>
              <li className="flex items-center gap-3">
                <Phone className="w-5 h-5 text-gray-400 shrink-0" />
                <a
                  href={`tel:${comision.telefono}`}
                  className="hover:text-blue-600 transition-colors"
                >
                  {comision.telefono}
                </a>
              </li>
            </ul>
          </div>
        </div>
      </section>

      {/* --- BOLSINES: FILTROS + LISTA + MAPA --- */}
      <section>
        <div className="flex items-center justify-between mb-4">
          <h2 className="text-xl font-bold text-gray-800 flex items-center gap-2">
            <Package className="w-5 h-5 text-blue-600" />
            Bolsines de esta comisión
          </h2>
          <span className="text-sm text-gray-500">
            {bolsinesFiltrados.length} de {bolsines.length} ·{" "}
            {selectedId ? "1 seleccionado" : "ninguno seleccionado"}
          </span>
        </div>

        {errorBolsines && (
          <div className="p-4 mb-4 text-red-500 bg-red-50 rounded-xl border border-red-100 text-sm">
            {errorBolsines}
          </div>
        )}

        {/* --- BARRA DE FILTROS --- */}
        <div className="bg-white border border-gray-200 rounded-xl p-4 mb-4 shadow-sm">
          <div className="flex items-center gap-2 mb-3 text-sm font-medium text-gray-700">
            <Filter className="w-4 h-4 text-gray-500" />
            Filtros
          </div>
          <div className="grid sm:grid-cols-[1fr_1fr_auto] gap-3">
            {/* Filtro precinto */}
            <div className="relative">
              <Search className="w-4 h-4 text-gray-400 absolute left-3 top-1/2 -translate-y-1/2 pointer-events-none" />
              <input
                type="text"
                value={filtroPrecinto}
                onChange={(e) => setFiltroPrecinto(e.target.value)}
                placeholder="Buscar por N° de precinto..."
                className="w-full pl-9 pr-3 py-2 text-sm border border-gray-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500/40 focus:border-blue-400 transition"
              />
            </div>

            {/* Filtro CM destino */}
            <select
              value={filtroCmDestino}
              onChange={(e) => setFiltroCmDestino(e.target.value)}
              className="w-full px-3 py-2 text-sm border border-gray-200 rounded-lg bg-white focus:outline-none focus:ring-2 focus:ring-blue-500/40 focus:border-blue-400 transition"
            >
              <option value="">Todas las CM destino</option>
              {cmDestinoDisponibles.map((cm) => (
                <option key={cm} value={cm}>
                  {cm}
                </option>
              ))}
            </select>

            {/* Limpiar */}
            <button
              onClick={limpiarFiltros}
              disabled={!hayFiltrosActivos}
              className={`inline-flex items-center justify-center gap-2 px-4 py-2 text-sm font-medium rounded-lg transition-colors ${
                hayFiltrosActivos
                  ? "bg-gray-100 text-gray-700 hover:bg-gray-200"
                  : "bg-gray-50 text-gray-400 cursor-not-allowed"
              }`}
            >
              <X className="w-4 h-4" />
              Limpiar
            </button>
          </div>
        </div>

        {bolsines.length === 0 && !errorBolsines ? (
          <div className="p-8 text-center text-gray-500 bg-gray-50 rounded-xl border border-dashed border-gray-200">
            No hay bolsines registrados para esta comisión.
          </div>
        ) : bolsinesFiltrados.length === 0 ? (
          <div className="p-8 text-center text-gray-500 bg-gray-50 rounded-xl border border-dashed border-gray-200">
            No hay bolsines que coincidan con los filtros aplicados.
            {hayFiltrosActivos && (
              <button
                onClick={limpiarFiltros}
                className="block mx-auto mt-3 text-sm text-blue-600 hover:text-blue-700 font-medium"
              >
                Limpiar filtros
              </button>
            )}
          </div>
        ) : (
          <div className="grid lg:grid-cols-[380px_1fr] gap-4">
            {/* --- LISTA IZQUIERDA --- */}
            <div className="space-y-2 max-h-120 overflow-y-auto pr-1">
              {bolsinesFiltrados.map((bolsin) => {
                const isSelected = selectedId === bolsin.id;

                return (
                  <div
                    key={bolsin.id}
                    onClick={() => handleSelect(bolsin.id)}
                    className={`
                      flex items-center justify-between p-3 rounded-xl border-2 cursor-pointer transition-all
                      ${
                        isSelected
                          ? "border-blue-500 bg-blue-50 shadow-md"
                          : "border-gray-200 bg-white hover:border-blue-300 hover:shadow-sm"
                      }
                    `}
                  >
                    <div className="flex items-center gap-3">
                      {isSelected ? (
                        <CheckCircle2 className="text-blue-500 w-5 h-5 shrink-0" />
                      ) : (
                        <Circle className="text-gray-300 w-5 h-5 shrink-0" />
                      )}

                      <div>
                        <div className="flex items-center gap-2 flex-wrap">
                          <span className="font-semibold text-gray-800 text-sm">
                            {bolsin.numeroBolsin}
                          </span>
                          <span className="text-[11px] font-medium px-2 py-0.5 bg-gray-100 text-gray-600 rounded-full">
                            Precinto: {bolsin.numeroPrecinto}
                          </span>
                        </div>
                        <div className="text-xs text-gray-500 mt-1">
                          {new Date(bolsin.fecha).toLocaleDateString()} ·{" "}
                          {bolsin.peso} kg
                        </div>
                        <div className="text-[11px] text-gray-500 mt-1">
                          Destino:{" "}
                          <span className="font-medium text-gray-700">
                            {bolsin.codigoComisionMedicaDestino}
                          </span>
                        </div>
                      </div>
                    </div>
                  </div>
                );
              })}
            </div>

            {/* --- MAPA DERECHA --- */}
            <div
              className="rounded-2xl overflow-hidden border border-gray-200 shadow-sm"
              style={{ height: 480 }}
            >
              <MapContainer
                center={mapCenter}
                zoom={13}
                style={{ height: "100%", width: "100%" }}
                scrollWheelZoom={true}
              >
                <TileLayer
                  attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                  url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />

                <FlyToSelected
                  bolsines={bolsinesFiltrados}
                  selectedId={selectedId}
                />
                <MapResizer />

                {bolsinesFiltrados.map((bolsin) => {
                  const isSelected = selectedId === bolsin.id;

                  return (
                    <Marker
                      key={bolsin.id}
                      position={[bolsin.latitud, bolsin.longitud]}
                      icon={isSelected ? selectedIcon : defaultIcon}
                      eventHandlers={{
                        click: () => handleSelect(bolsin.id),
                      }}
                    >
                      <Popup>
                        <div className="text-sm">
                          <strong>{bolsin.numeroBolsin}</strong>
                          <br />
                          Precinto: {bolsin.numeroPrecinto}
                          <br />
                          Peso: {bolsin.peso} kg
                          <br />
                          Destino: {bolsin.codigoComisionMedicaDestino}
                          <br />
                          {new Date(bolsin.fecha).toLocaleDateString()}
                        </div>
                      </Popup>
                    </Marker>
                  );
                })}
              </MapContainer>
            </div>
          </div>
        )}

        {/* --- CONFIRMAR SELECCIÓN (única) --- */}
        {selectedId && (
          <div className="mt-6 flex justify-end items-center gap-4">
            <span className="text-sm text-gray-600 font-medium">
              Bolsín seleccionado:{" "}
              <span className="text-gray-900">
                {bolsines.find((b) => b.id === selectedId)?.numeroBolsin}
              </span>
            </span>
            <button
              onClick={() => navigate(`/bolsines/${selectedId}/email`)}
              className="bg-blue-600 text-white px-6 py-2 rounded-lg font-medium hover:bg-blue-700 transition-colors cursor-pointer"
            >
              Confirmar Selección
            </button>
          </div>
        )}
      </section>
    </div>
  );
};