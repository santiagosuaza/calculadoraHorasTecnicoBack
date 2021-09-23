package com.IAS.calculadoraTecnico.service;

import com.IAS.calculadoraTecnico.Domain.Servicio;

import java.util.List;

public interface ServicioService {
    public Servicio createServicio (Servicio servicio);
    public Servicio BuscarServicio (Long id);
    public List<Servicio> buscarServiciosPorMesTecnico(Long cedulaTecnico, Long mes);
    public List<Servicio> calcularServiciosTecnicoporMes(Long cedulaTecnico,Long mesIni, Long mesFin);
    public Servicio updateServicio (Servicio servicio);
}
}

