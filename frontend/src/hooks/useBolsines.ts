import { useState, useEffect } from "react";
import type { Bolsin } from "../types/Bolsin";
import { seedBolsines } from "../mocks/seedTest";
import { bolsinesService } from "../services/bolsinesService";
import { MOCK_USE } from "../api/client";

type UseBolsinesOptions = {
  comision?: string;
  soloDestino?: boolean;
};

export const useBolsines = (options: UseBolsinesOptions = {}) => {
  const { comision, soloDestino } = options;
  const [bolsines, setBolsines] = useState<Bolsin[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchBolsines = async () => {
      try {
        setIsLoading(true);

        if (MOCK_USE) {
          await new Promise((r) => setTimeout(r, 300));
          const filtered = comision
            ? seedBolsines.filter((b) =>
                soloDestino
                  ? b.codigoComisionMedicaDestino === comision
                  : b.codigoComisionMedicaOrigen === comision ||
                    b.codigoComisionMedicaDestino === comision
              )
            : seedBolsines;
          setBolsines(filtered);
        } else {
          const data = await bolsinesService.listar({ comision, soloDestino });
          setBolsines(data);
        }

        setError(null);
      } catch (err) {
        setError("Error al cargar los bolsines");
        console.error(err);
      } finally {
        setIsLoading(false);
      }
    };

    fetchBolsines();
  }, [comision, soloDestino]);

  return { bolsines, isLoading, error };
};


// traer solo por id
export const useBolsin = (id?: string) => {
  const [bolsin, setBolsin] = useState<Bolsin | null>(null);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!id) {
      setBolsin(null);
      setIsLoading(false);
      return;
    }

    const fetchBolsin = async () => {
      try {
        setIsLoading(true);

        if (MOCK_USE) {
          await new Promise((r) => setTimeout(r, 300));
          const found = seedBolsines.find((b) => b.id === id) ?? null;
          setBolsin(found);
        } else {
          const data = await bolsinesService.obtener(id);
          setBolsin(data);
        }

        setError(null);
      } catch (err) {
        setError("Error al cargar el bolsín seleccionado");
        console.error(err);
      } finally {
        setIsLoading(false);
      }
    };

    fetchBolsin();
  }, [id]);

  return { bolsin, isLoading, error };
};