package com.IAS.calculadoraTecnico.controller;

import com.IAS.calculadoraTecnico.Domain.TrabajosSemanaTecnico;
import com.IAS.calculadoraTecnico.service.TecnicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/tecnico")
public class TecnicoController {

    @Autowired
    TecnicoService tecnicoService;

    @GetMapping("/{idTecnico}/{numSemana}")
    public TrabajosSemanaTecnico reporteSemanaTecnico(@PathVariable Long idTecnico, @PathVariable Long numSemana){
        return  tecnicoService.consultaPorSemanaTecnico(idTecnico, numSemana);
    }

}
