package com.bolsines.controller;

import com.bolsines.dto.response.BolsinResponse;
import com.bolsines.dto.response.IniciarResponse;
import com.bolsines.dto.response.NotificarEmailResponse;
import com.bolsines.service.GestorSeguimientodeBolsin;
import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bolsines/seguimiento")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PantallaSeguimientoDeBolsin {

    //! DESACOPLAMOS FRONTEND DE BACKEND POR ESO FALTAN ATRIBUTOS y/o metodos

    private final GestorSeguimientodeBolsin gestorSeguimientodeBolsin;

    // primer metodo inicia todo
    @GetMapping("/iniciar")
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
    public ResponseEntity<NotificarEmailResponse> opcionConfirmarCorreo() {

        // retorna un notificarResponse para notificar Ejecucion Exitosa
        var c = gestorSeguimientodeBolsin.tomarConfirmacionCorreo();
        return ResponseEntity.ok(c);
    }
}