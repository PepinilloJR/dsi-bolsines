
package com.bolsines.demo.App;

import com.bolsines.demo.Dto.BolsinResponse;
import com.bolsines.demo.Dto.ComisionMedicaResponse;
import com.bolsines.demo.Dto.LocalizacionBolsinResponse;
import com.bolsines.demo.Dto.MapaResponse;
import com.bolsines.demo.Dto.SeleccionarBolsinRequest;
import com.bolsines.demo.Dto.ConfirmarCorreoRequest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seguimiento-bolsines")
public class PantallaSeguimientoDeBolsin {

    private final GestorSeguimientoDeBolsin gestor;

    public PantallaSeguimientoDeBolsin(
            GestorSeguimientoDeBolsin gestor) {
        this.gestor = gestor;
    }

    // 1. Buscar Comisión Médica actual

    @GetMapping("/buscar-cm")
    public ResponseEntity<ComisionMedicaResponse>
            buscarCMActualEmpleado() {

        ComisionMedicaResponse respuesta =
                gestor.buscarCMActualEmpleado();

        return ResponseEntity.ok(respuesta);
    }

    // 2. Buscar bolsines enviados

    @GetMapping("/buscar-bolsines")
    public ResponseEntity<List<BolsinResponse>>
            buscarBolsinesEnviados() {

        List<BolsinResponse> respuesta =
                gestor.buscarBolsinesEnviados();

        return ResponseEntity.ok(respuesta);
    }

    // 3. Buscar localizaciones

    @GetMapping("/buscar-localizaciones")
    public ResponseEntity<List<LocalizacionBolsinResponse>>
            buscarLocalizacionesBolsines() {

        List<LocalizacionBolsinResponse> respuesta =
                gestor.buscarLocalizacionesBolsines();

        return ResponseEntity.ok(respuesta);
    }

    // 4. Obtener mapa

    @GetMapping("/obtener-mapa")
    public ResponseEntity<MapaResponse> obtenerMapa() {

        MapaResponse respuesta = gestor.obtenerMapa();

        return ResponseEntity.ok(respuesta);
    }

    // 5. Seleccionar bolsín en el mapa

    @GetMapping("/seleccionar-bolsin")
    public ResponseEntity<Void> tomarSeleccionBolsinMapa(
            @RequestBody SeleccionarBolsinRequest request) {

        gestor.tomarSeleccionBolsinMapa(
                request.numeroBolsin()
        );

        return ResponseEntity.ok().build();
    }

    // 6. Confirmar envío del correo

    @PostMapping("/confirmar-correo")
    public ResponseEntity<Void> tomarConfirmacionCorreo(
            @RequestBody ConfirmarCorreoRequest request) {

        gestor.tomarConfirmacionCorreo(
                request.confirmacion()
        );

        return ResponseEntity.ok().build();
    }
}
