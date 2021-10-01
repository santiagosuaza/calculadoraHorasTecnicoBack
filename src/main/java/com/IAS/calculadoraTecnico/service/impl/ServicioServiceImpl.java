package com.IAS.calculadoraTecnico.service.impl;

import com.IAS.calculadoraTecnico.Domain.Servicio;
import com.IAS.calculadoraTecnico.Domain.Tecnico;
import com.IAS.calculadoraTecnico.repository.ServicioRepository;
import com.IAS.calculadoraTecnico.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioServiceImpl implements ServicioService {

    @Autowired
    ServicioRepository servicioRepository;
    @Override
    public Servicio createServicio(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    @Override
    public List<Servicio>  findByTecnico(Optional<Tecnico> tecnico) {
        return servicioRepository.findByTecnico(tecnico);
    }


}
