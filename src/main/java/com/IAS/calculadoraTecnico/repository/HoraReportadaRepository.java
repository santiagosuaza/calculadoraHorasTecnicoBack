package com.IAS.calculadoraTecnico.repository;


import com.IAS.calculadoraTecnico.Domain.HoraReportada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoraReportadaRepository extends JpaRepository<HoraReportada,Long> {

}
