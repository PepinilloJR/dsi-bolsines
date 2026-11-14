import { useState, useEffect } from "react";
import type { ComisionMedica } from "../types/ComisionMedica";
import { seedComisiones, MOCK_USE } from "../mocks/seedTest";

export const useComisionMedica = (codigo: string | undefined) => {
  const [comision, setComision] = useState<ComisionMedica | null>(null);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!codigo) {
      setIsLoading(false);
      setError("Código de comisión no proporcionado");
      return;
    }

    const fetchComision = async () => {
      try {
        setIsLoading(true);

        if (MOCK_USE) {
          await new Promise((r) => setTimeout(r, 300));
          const found = seedComisiones.find((c) => c.codigo === codigo);
          if (!found) throw new Error("Comisión no encontrada");
          setComision(found);
        } else {
          const response = await fetch(`/api/comisiones/${codigo}`);
          if (!response.ok) throw new Error("Error en la respuesta");
          const data: ComisionMedica = await response.json();
          setComision(data);
        }

        setError(null);
      } catch (err) {
        setError(
          err instanceof Error ? err.message : "Error al cargar la comisión"
        );
        setComision(null);
      } finally {
        setIsLoading(false);
      }
    };

    fetchComision();
  }, [codigo]);

  return { comision, isLoading, error };
};