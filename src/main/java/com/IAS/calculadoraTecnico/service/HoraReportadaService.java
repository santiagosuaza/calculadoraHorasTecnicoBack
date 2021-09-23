package com.IAS.calculadoraTecnico.service;

import com.IAS.calculadoraTecnico.Domain.HoraReportada;

import java.util.Optional;

public interface HoraReportadaService {
    public HoraReportada createHoraReportada (HoraReportada horaReportada);
    public Optional<HoraReportada> getHoraReportada (Long idHoraReportada);
    public HoraReportada updateHoraReportada (HoraReportada horaReportada);
}
