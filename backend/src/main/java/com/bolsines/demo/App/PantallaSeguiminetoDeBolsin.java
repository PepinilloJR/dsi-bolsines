package com.bolsines.demo.App;

import com.bolsines.demo.Dto.LocalizacionBolsinResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/bolsin/localizacion")
@AllArgsConstructor
@NoArgsConstructor
public class PantallaSeguiminetoDeBolsin {

    private GestorSeguimientoDeBolsin gestorSeguimientoDeBolsin;

    @GetMapping("/")
    public ResponseEntity<List<LocalizacionBolsinResponse>> mostrarLocalizacionesBolsin() {
        List<LocalizacionBolsinResponse> localizaciones = gestorSeguimientoDeBolsin.buscarLocalizacionesBolsines();
        return ResponseEntity.ok(localizaciones);
    }

}
