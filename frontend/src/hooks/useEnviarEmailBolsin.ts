import { useState, useCallback } from "react";
import type { Bolsin } from "../types/Bolsin";
import { MOCK_USE } from "../api/client";
import { emailService } from "../services/emailService";

type Estado = "idle" | "sending" | "success" | "error";

type UseEnviarEmailBolsinResult = {
  estado: Estado;
  error: string | null;
  destinatario: string | null;
  enviar: (bolsin: Bolsin, gerenteEmail: string) => Promise<void>;
  reset: () => void;
};

export const useEnviarEmailBolsin = (): UseEnviarEmailBolsinResult => {
  const [estado, setEstado] = useState<Estado>("idle");
  const [error, setError] = useState<string | null>(null);
  const [destinatario, setDestinatario] = useState<string | null>(null);

  const reset = useCallback(() => {
    setEstado("idle");
    setError(null);
    setDestinatario(null);
  }, []);

  const enviar = useCallback(
    async (bolsin: Bolsin, gerenteEmail: string) => {
      try {
        setEstado("sending");
        setError(null);

        if (MOCK_USE) {
          await new Promise((r) => setTimeout(r, 2000));
          setDestinatario(gerenteEmail);
        } else {
          const data = await emailService.enviarUbicacionBolsin({
            bolsinId: bolsin.id,
            gerenteEmail,
            ubicacion: { lat: bolsin.latitud, lng: bolsin.longitud },
            numeroBolsin: bolsin.numeroBolsin,
            numeroPrecinto: bolsin.numeroPrecinto,
          });
          setDestinatario(data.destinatario ?? gerenteEmail);
        }

        setEstado("success");
      } catch (err) {
        setError(
          err instanceof Error ? err.message : "Error al enviar el correo"
        );
        setEstado("error");
      }
    },
    []
  );

  return { estado, error, destinatario, enviar, reset };
};