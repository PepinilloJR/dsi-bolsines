package com.bolsines.controller;

// import java.util.List;
// import com.bolsines.dto.response.ComisionResponse;
import com.bolsines.dto.response.IniciarResponse;
import com.bolsines.service.GestorSeguimientodeBolsin;
import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
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

}