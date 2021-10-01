package com.IAS.calculadoraTecnico.repository;

import com.IAS.calculadoraTecnico.Domain.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TecnicoRepository extends JpaRepository<Tecnico,Long> {
    public  Tecnico findByCedula(Long Cedula);

}
