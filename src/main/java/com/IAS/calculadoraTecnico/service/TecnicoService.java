package com.IAS.calculadoraTecnico.service;

import com.IAS.calculadoraTecnico.Domain.Tecnico;
import com.IAS.calculadoraTecnico.Domain.TrabajosSemanaTecnico;

public interface TecnicoService {
    public Tecnico findTecnicoPorCedula(Long cedulaTecnico);

    public Tecnico createTecnico(Tecnico tecnico);

    public TrabajosSemanaTecnico consultaPorSemanaTecnico(Long idTecnico, Long numSemana);


}