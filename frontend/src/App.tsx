import { BrowserRouter, Routes, Route } from "react-router-dom";
import { Layout } from "./components/Layout";
import { HomeScreen } from "./screens/HomeScreen";
import { ComisionMedicaScreen } from "./screens/ComisionMedicaScreen";
import { EmailConfirmScreen } from "./screens/EmailConfirmScreen";


export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* El Layout envuelve a todas las rutas hijas */}
        <Route element={<Layout />}>
          <Route path="/" element={<HomeScreen />} />
          <Route path="/comisiones" element={<ComisionMedicaScreen />} />
          <Route path="/comisiones/:codigo" element={<ComisionMedicaScreen />} />

          <Route path="/bolsines/:id/email" element={<EmailConfirmScreen />} />


        </Route>
      </Routes>
    </BrowserRouter>
  );
}