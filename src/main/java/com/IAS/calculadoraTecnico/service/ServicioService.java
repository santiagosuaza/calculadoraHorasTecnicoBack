package com.IAS.calculadoraTecnico.service;

import com.IAS.calculadoraTecnico.Domain.Servicio;
import com.IAS.calculadoraTecnico.Domain.Tecnico;

import java.util.List;
import java.util.Optional;

public interface ServicioService {
    public Servicio createServicio (Servicio servicio);

    public List<Servicio> findByTecnico (Optional<Tecnico> tecnico);

}


