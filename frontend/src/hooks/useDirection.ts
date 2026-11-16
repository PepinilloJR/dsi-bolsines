import { useState, useEffect } from "react";

/*
[https://maps.googleapis.com/maps/api/geocode/json?latlng=$](https://maps.googleapis.com/maps/api/geocode/json?latlng=$){lat},${lng}&key=${import.meta.env.VITE_GOOGLE_MAPS_API_KEY}&language=es
*/
export const useDireccion = (lat?: number, lng?: number) => {
  const [direccion, setDireccion] = useState<string | null>(null);
  const [cargando, setCargando] = useState<boolean>(false);

  useEffect(() => {
    if (lat == null || lng == null) return;

    const controller = new AbortController();

    const obtenerCalle = async () => {
      try {
        setCargando(true);
        const res = await fetch(
          `https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=${lat}&lon=${lng}&accept-language=es`,
          { signal: controller.signal }
        );
        const data = await res.json();

        // Armamos calle + altura + ciudad de forma limpia
        const { road, house_number, city, town, state } = data.address ?? {};
        const calle = road ? `${road}${house_number ? ` ${house_number}` : ""}` : null;
        const localidad = city || town || state;

        if (calle) {
          setDireccion(localidad ? `${calle}, ${localidad}` : calle);
        } else {
          setDireccion(data.display_name?.split(",").slice(0, 2).join(",") ?? null);
        }
      } catch (err) {
        if ((err as Error).name !== "AbortError") {
          setDireccion(null);
        }
      } finally {
        setCargando(false);
      }
    };

    obtenerCalle();
    return () => controller.abort();
  }, [lat, lng]);

  return { direccion, cargando };
};