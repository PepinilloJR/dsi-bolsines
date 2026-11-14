import { Outlet, Link } from "react-router-dom";
import { Package, LogIn } from "lucide-react";

export const Layout = () => {
  return (
    <div className="min-h-screen bg-gray-50 flex flex-col font-sans">
      {/* --- BARRA DE NAVEGACIÓN --- */}
      <nav className="bg-white shadow-sm border-b border-gray-200 sticky top-0 z-10">
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
              <button className="flex items-center gap-2 text-sm font-medium text-gray-600 hover:text-blue-600 transition-colors">
                <LogIn className="w-4 h-4" />
                Iniciar Sesión
              </button>
            </div>
          </div>
        </div>
      </nav>

      {/* --- CONTENIDO DE CADA VISTA --- */}
      <main className="grow">
        <Outlet />
      </main>

      {/* --- FOOTER --- */}
      <footer className="bg-gray-900 text-gray-400 py-8 text-center text-sm">
        <p>© {new Date().getFullYear()} LogísticaApp. Todos los derechos reservados.</p>
      </footer>
    </div>
  );
};