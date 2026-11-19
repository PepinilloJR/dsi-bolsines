import { useState } from "react";
import { Outlet, Link } from "react-router-dom";
import { Package, LogIn, User } from "lucide-react";

const USUARIOS_MOCK = {
  "11111111-1111-1111-1111-111111111111": {
    sesionId: "11111111-1111-1111-1111-111111111111",
    username: "jperez",
    nombre: "Juan Pérez",
    email: "jperez@srt.gob.ar",
    comision: "CM-CBA-01",
    rol: "GCM",
  },
  "11111111-1111-1111-1111-111111111112": {
    sesionId: "11111111-1111-1111-1111-111111111112",
    username: "mgomez",
    nombre: "María Gómez",
    email: "mgomez@srt.gob.ar",
    comision: "CM-ROS-02",
    rol: "OPERADOR",
  },
} as const;

type SesionKey = keyof typeof USUARIOS_MOCK;

export const Layout = () => {
  const [sesionKey, setSesionKey] = useState<SesionKey>(
    "11111111-1111-1111-1111-111111111111"
  );
  const usuarioLogueado = USUARIOS_MOCK[sesionKey];

  return (
    <div className="min-h-screen bg-gray-50 flex flex-col font-sans">
      {/* --- BARRA DE NAVEGACIÓN --- */}
      <nav className="bg-white shadow-sm border-b border-gray-200 sticky top-0 z-50">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between h-16 items-center">
            <Link to="/" className="flex items-center gap-2">
              <div className="bg-blue-600 p-2 rounded-lg">
                <Package className="w-6 h-6 text-white" />
              </div>
              <span className="font-bold text-xl text-gray-800 tracking-tight">
                Logística<span className="text-blue-600">App</span>
              </span>
            </Link>

            <div>
              {usuarioLogueado ? (
                <div className="flex items-center gap-3">
                  {/* Selector rápido de sesión mock */}
                  <select
                    aria-label="Cambiar sesión simulada"
                    value={sesionKey}
                    onChange={(e) => setSesionKey(e.target.value as SesionKey)}
                    className="text-xs bg-gray-100 border border-gray-300 text-gray-700 rounded-md px-2 py-1.5 font-medium focus:outline-none focus:ring-2 focus:ring-blue-500 cursor-pointer"
                  >
                    {Object.values(USUARIOS_MOCK).map((u) => (
                      <option key={u.sesionId} value={u.sesionId}>
                        {u.nombre} ({u.comision})
                      </option>
                    ))}
                  </select>

                  <div className="text-right hidden sm:block">
                    <p className="text-sm font-medium text-gray-800 leading-none">
                      {usuarioLogueado.nombre}{" "}
                      <span className="text-[10px] uppercase bg-gray-200 text-gray-600 px-1.5 py-0.5 rounded ml-1">
                        {usuarioLogueado.rol}
                      </span>
                    </p>
                    <p className="text-xs text-gray-500 mt-1">
                      {usuarioLogueado.email} ·{" "}
                      <span className="font-semibold text-blue-600">
                        {usuarioLogueado.comision}
                      </span>
                    </p>
                  </div>

                  <div className="bg-blue-50 border border-blue-200 p-2 rounded-full text-blue-600">
                    <User className="w-4 h-4" />
                  </div>
                </div>
              ) : (
                <button className="flex items-center gap-2 text-sm font-medium text-gray-600 hover:text-blue-600 transition-colors">
                  <LogIn className="w-4 h-4" />
                  Iniciar Sesión
                </button>
              )}
            </div>
          </div>
        </div>
      </nav>

      {/* --- CONTENIDO DE CADA VISTA --- */}
      <main className="grow">
        <Outlet context={{ usuarioLogueado }} />
      </main>

      {/* --- FOOTER --- */}
      <footer className="bg-gray-900 text-gray-400 py-8 text-center text-sm">
        <p>© {new Date().getFullYear()} LogísticaApp. Todos los derechos reservados.</p>
      </footer>
    </div>
  );
};