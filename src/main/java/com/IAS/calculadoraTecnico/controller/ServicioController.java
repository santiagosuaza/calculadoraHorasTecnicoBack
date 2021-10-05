package com.IAS.calculadoraTecnico.controller;


import com.IAS.calculadoraTecnico.Domain.Servicio;
import com.IAS.calculadoraTecnico.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/servicio")
public class ServicioController {
    @Autowired
    ServicioService servicioService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Servicio> createCargo(@RequestBody Servicio servicio){
        return ResponseEntity.ok().body(servicioService.createServicio(servicio));



    }

}
