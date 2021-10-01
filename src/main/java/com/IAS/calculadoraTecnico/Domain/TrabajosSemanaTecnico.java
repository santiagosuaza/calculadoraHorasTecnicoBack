package com.IAS.calculadoraTecnico.Domain;

import lombok.Data;

@Data
public class TrabajosSemanaTecnico {
    Long idTecnico;
    int horasSemanaNormales;
    int horasNocturnas;
    int horasExtraNormales;
    int horasDominicales;


}
