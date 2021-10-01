package com.IAS.calculadoraTecnico.service.impl;

import com.IAS.calculadoraTecnico.Domain.HoraReportada;
import com.IAS.calculadoraTecnico.repository.HoraReportadaRepository;
import com.IAS.calculadoraTecnico.service.HoraReportadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HoraReportadaServiceImpl  implements HoraReportadaService {

    @Autowired
    HoraReportadaRepository horaReportadaRepository;

    @Override
    public HoraReportada createHoraReportada(HoraReportada horaReportada) {
        return horaReportadaRepository.save(horaReportada);
    }

    @Override
    public HoraReportada getHoraReportada(Long idHoraReportada) {
        return horaReportadaRepository.getById(idHoraReportada);
    }


}
