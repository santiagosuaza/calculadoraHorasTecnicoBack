package com.IAS.calculadoraTecnico.service;

import com.IAS.calculadoraTecnico.Domain.Tecnico;

import java.util.List;

public interface TecnicoService {
    public Tecnico findTecnicoPorCedula(Long cedulaTecnico);
    public Tecnico createTecnico(Tecnico tecnico);
    public Tecnico updateTecnico(Tecnico tecnico);
}
}
}
