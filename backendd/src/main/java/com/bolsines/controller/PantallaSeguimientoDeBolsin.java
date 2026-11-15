package com.bolsines.controller;

import com.bolsines.dto.response.BolsinResponse;
// import java.util.List;
// import com.bolsines.dto.response.ComisionResponse;
import com.bolsines.dto.response.IniciarResponse;
import com.bolsines.service.GestorSeguimientodeBolsin;
import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bolsines/seguimiento")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PantallaSeguimientoDeBolsin {

    private final GestorSeguimientodeBolsin gestorSeguimientodeBolsin;

    @PostMapping("/iniciar")
    public ResponseEntity<IniciarResponse> opcionConsultarUbicacionBolsines() {
        var c = habilitarPantallaSeguimientoBolsin();
        return ResponseEntity.ok(c);
    }

    private IniciarResponse habilitarPantallaSeguimientoBolsin() {
        return gestorSeguimientodeBolsin.consultarUbicacionBolsines();
    }

    // pantalla pre confirmar envio
    @GetMapping("/{id}")
    public ResponseEntity<BolsinResponse> opcionSelecBolsinMapa(@PathVariable("id") Long numeroBolsin) {
        var b = gestorSeguimientodeBolsin.tomarSeleccionBolsinMapa(numeroBolsin);
        return ResponseEntity.ok(b);
    }

    // 6. Confirmar envío del correo
    @PostMapping("/confirmar-correo")
    public ResponseEntity<Void> opcionConfirmarCorreo() {

        gestorSeguimientodeBolsin.tomarConfirmacionCorreo();

        return ResponseEntity.ok().build();
    }
}