import { Link } from "react-router-dom";
import { Package, MapPin, Truck, ArrowRight } from "lucide-react";

export const HomeScreen = () => {
  return (
    <>
      {/* --- SECCIÓN PRINCIPAL (HERO) --- */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16 md:py-24">
        <div className="grid md:grid-cols-2 gap-12 items-center">
          
          {/* Columna Izquierda: Texto */}
          <div className="space-y-8">
            <div className="inline-block bg-blue-100 text-blue-700 px-4 py-1.5 rounded-full text-sm font-semibold tracking-wide">
              Sistema de Gestión de Envíos
            </div>
            <h1 className="text-4xl md:text-5xl font-extrabold text-gray-900 leading-tight">
              Gestión inteligente de <span className="text-blue-600">Bolsines</span> y distribución.
            </h1>
            <p className="text-lg text-gray-600 leading-relaxed">
              Optimiza tus rutas, controla los precintos y administra el peso de tus envíos de manera ágil y segura. La herramienta definitiva para tu equipo de logística.
            </p>
            <div className="flex flex-col sm:flex-row gap-4">
              <Link
                to="/comisiones/CM-0107"
                className="flex items-center justify-center gap-2 bg-blue-600 text-white px-8 
                  py-3 rounded-lg font-semibold hover:bg-blue-700 transition-all shadow-lg shadow-blue-600/30"
              >
                Consultar Ubicación Bolsines
                <ArrowRight className="w-5 h-5" />
              </Link>
              <a
                href="/PPAI2026_3K1_G11_E2_Disenio.pdf"
                target="_blank"
                rel="noopener noreferrer"
                className="flex items-center justify-center gap-2 bg-white text-gray-700 border-2 border-gray-200 px-8 py-3 rounded-lg font-semibold hover:border-gray-300 hover:bg-gray-50 transition-all"
              >
                Ver documentación
              </a>
            </div>
          </div>

          {/* Columna Derecha: Imagen */}
          <div className="relative">
            <div className="absolute inset-0 bg-blue-100 rounded-full blur-3xl opacity-50 transform scale-110 -z-10"></div>
            <div className="bg-white p-4 rounded-3xl shadow-2xl border border-gray-100 rotate-2 hover:rotate-0 transition-transform duration-500">
              <img
                src="image/image.png"
                alt="Ilustración de trabajador logístico"
                className="w-full h-auto rounded-2xl object-cover"
                onError={(e) => {
                  (e.target as HTMLImageElement).src =
                    "https://placehold.co/600x400/e2e8f0/64748b?text=Imagen+no+encontrada";
                }}
              />
            </div>
          </div>

        </div>
      </div>

      {/* --- TARJETAS DE ACCESO RÁPIDO (SIN LINKS) --- */}
      <div className="bg-white border-t border-gray-100 py-16">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <h2 className="text-2xl font-bold text-gray-800 mb-8 text-center">Accesos Rápidos</h2>
          <div className="grid md:grid-cols-3 gap-6">

            <div className="p-6 rounded-2xl bg-gray-50 border border-gray-100">
              <div className="w-12 h-12 bg-blue-100 text-blue-600 rounded-xl flex items-center justify-center mb-4">
                <Package className="w-6 h-6" />
              </div>
              <h3 className="font-bold text-gray-800 text-lg mb-2">Bolsines</h3>
              <p className="text-gray-500 text-sm">Revisa el inventario actual y selecciona los bolsines para despacho.</p>
            </div>

            <div className="p-6 rounded-2xl bg-gray-50 border border-gray-100">
              <div className="w-12 h-12 bg-green-100 text-green-600 rounded-xl flex items-center justify-center mb-4">
                <MapPin className="w-6 h-6" />
              </div>
              <h3 className="font-bold text-gray-800 text-lg mb-2">Sucursales</h3>
              <p className="text-gray-500 text-sm">Gestiona los puntos de entrega y el estado de cada sucursal en el mapa.</p>
            </div>

            <div className="p-6 rounded-2xl bg-gray-50 border border-gray-100">
              <div className="w-12 h-12 bg-orange-100 text-orange-600 rounded-xl flex items-center justify-center mb-4">
                <Truck className="w-6 h-6" />
              </div>
              <h3 className="font-bold text-gray-800 text-lg mb-2">Flota de Vehículos</h3>
              <p className="text-gray-500 text-sm">Asigna vehículos, controla capacidades y monitorea las rutas de reparto.</p>
            </div>

          </div>
        </div>
      </div>
    </>
  );
};