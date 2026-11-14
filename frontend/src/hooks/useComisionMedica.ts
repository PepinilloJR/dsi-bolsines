import { useState, useEffect } from "react";
import type { ComisionMedica } from "../types/ComisionMedica";
import { seedComisiones } from "../mocks/seedTest";
import { comisionesService } from "../services/comisionesService";
import { MOCK_USE } from "../api/client";


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
          const data = await comisionesService.obtener(codigo);
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