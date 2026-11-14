// src/screens/EmailConfirmScreen.tsx
import { useParams, Link, useNavigate } from "react-router-dom";
import { useMemo } from "react";
import {
  ArrowLeft,
  Mail,
  MapPin,
  CheckCircle2,
  Loader2,
  AlertCircle,
  Send,
} from "lucide-react";
import { useBolsines } from "../hooks/useBolsines";
import { useComisionMedica } from "../hooks/useComisionMedica";
import { useEnviarEmailBolsin } from "../hooks/useEnviarEmailBolsin";

export const EmailConfirmScreen = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const { bolsines, isLoading: loadingBolsines } = useBolsines();
  const bolsin = useMemo(
    () => bolsines.find((b) => b.id === id),
    [bolsines, id]
  );

  // Traemos la comisión destino para mostrar el gerente/email
  const { comision: comisionDestino, isLoading: loadingComision } =
    useComisionMedica(bolsin?.codigoComisionMedicaDestino);

  const { estado, error, destinatario, enviar } = useEnviarEmailBolsin();

  // --- Loading inicial ---
  if (loadingBolsines || loadingComision) {
    return (
      <div className="max-w-3xl mx-auto p-6 text-center text-gray-500">
        <Loader2 className="w-6 h-6 animate-spin mx-auto mb-2" />
        Cargando datos...
      </div>
    );
  }

  // --- Bolsín inexistente ---
  if (!bolsin) {
    return (
      <div className="max-w-3xl mx-auto p-6">
        <Link
          to="/"
          className="inline-flex items-center gap-2 text-sm text-gray-500 hover:text-blue-600 mb-4 cursor-pointer"
        >
          <ArrowLeft className="w-4 h-4" />
          Volver al inicio
        </Link>
        <div className="p-8 text-center text-red-500 bg-red-50 rounded-xl border border-red-100">
          Bolsín no encontrado.
        </div>
      </div>
    );
  }

  // ============================
  // ESTADO: ENVIANDO
  // ============================
  if (estado === "sending") {
    return (
      <div className="max-w-3xl mx-auto p-6">
        <div className="bg-white border border-gray-200 rounded-2xl shadow-md p-10 flex flex-col items-center justify-center text-center space-y-4">
          <Loader2 className="w-12 h-12 text-blue-600 animate-spin" />
          <h2 className="text-lg font-semibold text-gray-800">
            Enviando correo electrónico...
          </h2>
          <p className="text-sm text-gray-500 max-w-sm">
            Estamos notificando al Gerente de la Comisión Médica destino sobre la
            ubicación del bolsín <strong>{bolsin.numeroBolsin}</strong>.
          </p>
        </div>
      </div>
    );
  }

  // ============================
  // ESTADO: ÉXITO (CU-31 completado)
  // ============================
  if (estado === "success") {
    return (
      <div className="max-w-3xl mx-auto p-6 space-y-6">
        <div className="bg-white border border-green-200 rounded-2xl shadow-md overflow-hidden">
          <div className="bg-green-50 px-6 py-8 flex flex-col items-center text-center space-y-3">
            <div className="w-14 h-14 bg-green-100 rounded-full flex items-center justify-center">
              <CheckCircle2 className="w-8 h-8 text-green-600" />
            </div>
            <h1 className="text-2xl font-bold text-gray-900">
              Correo enviado exitosamente
            </h1>
            <p className="text-sm text-gray-600 max-w-md">
              Se informó al Gerente de la Comisión Médica destino sobre la
              ubicación del bolsín <strong>{bolsin.numeroBolsin}</strong>.
            </p>
          </div>

          <div className="p-6 space-y-3 text-sm text-gray-600">
            <div className="flex items-center gap-3">
              <Mail className="w-4 h-4 text-gray-400 shrink-0" />
              <span>
                Destinatario:{" "}
                <span className="font-medium text-gray-800">
                  {destinatario ?? comisionDestino?.email}
                </span>
              </span>
            </div>
            <div className="flex items-center gap-3">
              <MapPin className="w-4 h-4 text-gray-400 shrink-0" />
              <span>
                Ubicación informada:{" "}
                <span className="font-mono text-xs text-gray-700">
                  {bolsin.latitud.toFixed(6)}, {bolsin.longitud.toFixed(6)}
                </span>
              </span>
            </div>
          </div>
        </div>

        <div className="flex justify-end">
          <button
            onClick={() => navigate(`/comisiones/${bolsin.codigoComisionMedicaOrigen}`)}
            className="bg-blue-600 text-white px-6 py-2 rounded-lg font-medium hover:bg-blue-700 transition-colors cursor-pointer"
          >
            Volver a la comisión
          </button>
        </div>
      </div>
    );
  }

  // ============================
  // ESTADO: ERROR
  // ============================
  if (estado === "error") {
    return (
      <div className="max-w-3xl mx-auto p-6 space-y-4">
        <div className="bg-white border border-red-200 rounded-2xl shadow-md p-8 flex flex-col items-center text-center space-y-3">
          <AlertCircle className="w-12 h-12 text-red-500" />
          <h2 className="text-lg font-semibold text-gray-800">
            No se pudo enviar el correo
          </h2>
          <p className="text-sm text-gray-600">{error}</p>
          <div className="flex gap-3 mt-2">
            <button
              onClick={() =>
                comisionDestino?.email &&
                enviar(bolsin, comisionDestino.email)
              }
              className="bg-blue-600 text-white px-5 py-2 rounded-lg font-medium hover:bg-blue-700 transition-colors cursor-pointer"
            >
              Reintentar
            </button>
            <button
              onClick={() => navigate(-1)}
              className="bg-gray-100 text-gray-700 px-5 py-2 rounded-lg font-medium hover:bg-gray-200 transition-colors cursor-pointer"
            >
              Cancelar
            </button>
          </div>
        </div>
      </div>
    );
  }

  // ============================
  // ESTADO: CONFIRM (inicial)
  // ============================
  return (
    <div className="max-w-3xl mx-auto p-6 space-y-6">
      <button
        onClick={() => navigate(-1)}
        className="inline-flex items-center gap-2 text-sm text-gray-500 hover:text-blue-600 transition-colors cursor-pointer"
      >
        <ArrowLeft className="w-4 h-4" />
        Volver
      </button>

      <div className="bg-white border border-gray-200 rounded-2xl shadow-md overflow-hidden">
        <div className="bg-blue-50 px-6 py-6 flex items-center gap-3">
          <div className="w-12 h-12 bg-blue-100 rounded-full flex items-center justify-center">
            <Mail className="w-6 h-6 text-blue-600" />
          </div>
          <div>
            <h1 className="text-lg font-bold text-gray-900">
              ¿Enviar correo electrónico?
            </h1>
            <p className="text-sm text-gray-600">
              Se notificará al Gerente de la Comisión Médica destino.
            </p>
          </div>
        </div>

        <div className="p-6 space-y-4 text-sm">
          <div className="bg-gray-50 border border-gray-100 rounded-xl p-4 space-y-2">
            <div className="flex items-start gap-3">
              <span className="text-gray-500 w-24 shrink-0">Bolsín:</span>
              <span className="font-medium text-gray-800">
                {bolsin.numeroBolsin}{" "}
                <span className="text-gray-500 font-normal">
                  (Precinto {bolsin.numeroPrecinto})
                </span>
              </span>
            </div>
            <div className="flex items-start gap-3">
              <span className="text-gray-500 w-24 shrink-0">CM Destino:</span>
              <span className="font-medium text-gray-800">
                {bolsin.codigoComisionMedicaDestino}
                {comisionDestino && (
                  <span className="text-gray-500 font-normal">
                    {" "}
                    — {comisionDestino.nombre}
                  </span>
                )}
              </span>
            </div>
            <div className="flex items-start gap-3">
              <span className="text-gray-500 w-24 shrink-0">Gerente:</span>
              <span className="font-medium text-gray-800 break-all">
                {comisionDestino?.email ?? "—"}
              </span>
            </div>
            <div className="flex items-start gap-3">
              <span className="text-gray-500 w-24 shrink-0">Ubicación:</span>
              <span className="font-mono text-xs text-gray-700">
                {bolsin.latitud.toFixed(6)}, {bolsin.longitud.toFixed(6)}
              </span>
            </div>
          </div>

          <p className="text-xs text-gray-500">
            El correo incluirá la ubicación actual del bolsín en el mapa.
          </p>
        </div>

        <div className="px-6 py-4 bg-gray-50 border-t border-gray-100 flex justify-end gap-3">
          <button
            onClick={() => navigate(-1)}
            className="bg-white border border-gray-200 text-gray-700 px-5 py-2 rounded-lg 
              font-medium hover:bg-gray-100 transition-colors cursor-pointer"
          >
            Cancelar
          </button>
          <button
            onClick={() =>
              comisionDestino?.email && enviar(bolsin, comisionDestino.email)
            }
            disabled={!comisionDestino?.email}
            className="inline-flex items-center gap-2 bg-blue-600 text-white px-5 py-2 
              rounded-lg font-medium hover:bg-blue-700 transition-colors disabled:bg-gray-300 
              disabled:cursor-not-allowed cursor-pointer"
          >
            <Send className="w-4 h-4" />
            Confirmar envío
          </button>
        </div>
      </div>
    </div>
  );
};