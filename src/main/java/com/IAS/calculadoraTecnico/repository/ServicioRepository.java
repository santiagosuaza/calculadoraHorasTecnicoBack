package com.IAS.calculadoraTecnico.repository;

import com.IAS.calculadoraTecnico.Domain.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio,Long> {
}
