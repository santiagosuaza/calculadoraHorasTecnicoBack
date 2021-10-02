package com.IAS.calculadoraTecnico.Domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TrabajosSemanaTecnico {
    Long idTecnico;
    int horasSemanaNormales;
    int horasNocturnas;
    int horasExtraNormales;
    int horasDominicales;

    public TrabajosSemanaTecnico() {
    }
}
