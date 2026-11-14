import { useState, useEffect } from "react";
import type { Bolsin } from "../types/Bolsin";
import { seedBolsines, MOCK_USE } from "../mocks/seedTest";

export const useBolsines = () => {
  const [bolsines, setBolsines] = useState<Bolsin[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchBolsines = async () => {
      try {
        setIsLoading(true);

        if (MOCK_USE) {
          // Simulamos un pequeño delay para que se vea el loading
          await new Promise((r) => setTimeout(r, 300));
          setBolsines(seedBolsines);
        } else {
          // TODO THIS SHIT COMPLETE
          const response = await fetch("/api/bolsines");
          if (!response.ok) throw new Error("Error en la respuesta");
          const data: Bolsin[] = await response.json();
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
  }, []);

  return { bolsines, isLoading, error };
};