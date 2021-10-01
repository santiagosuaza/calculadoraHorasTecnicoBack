package com.IAS.calculadoraTecnico.service.impl;

import com.IAS.calculadoraTecnico.Domain.Servicio;
import com.IAS.calculadoraTecnico.Domain.Tecnico;
import com.IAS.calculadoraTecnico.Domain.TrabajosSemanaTecnico;
import com.IAS.calculadoraTecnico.repository.TecnicoRepository;
import com.IAS.calculadoraTecnico.service.ServicioService;
import com.IAS.calculadoraTecnico.service.TecnicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@Service
public class TecnicoServiceImpl implements TecnicoService {
    @Autowired
    TecnicoRepository tecnicoRepository;
    int numeroSemana = 0;
    Servicio servicio;
    @Autowired
    ServicioService servicioService;
    TrabajosSemanaTecnico trabajosSemanaTecnico;
    int horasSemanaNormales = 0;
    int horasDominicales = 0;
    int horasNocturnas = 0;
    int horasExtraNormales = 0;
    int horasExtraDominicales = 0;
    boolean flag = true;
    int maxHor = 48;
    int contHorasPorServicio = 0;

    Calendar calendarDiaIni = Calendar.getInstance();;
    Calendar calendarDiaFin = Calendar.getInstance();;

    Calendar calendarDiaCont = Calendar.getInstance();
    LocalDateTime diaContador;
    LocalDateTime fechaIni;
    LocalDateTime fechaFini;
    int contDias;

    @Override
    public Tecnico findTecnicoPorCedula(Long cedulaTecnico) {
        return tecnicoRepository.findByCedula(cedulaTecnico);
    }

    @Override
    public Tecnico createTecnico(Tecnico tecnico) {
        return tecnicoRepository.save(tecnico);
    }

    @Override
    public TrabajosSemanaTecnico consultaPorSemanaTecnico(Long idTecnico, Long numSemana) {
        ArrayList<Servicio> serviciosTecnico = new ArrayList<Servicio>();
        serviciosTecnico = (ArrayList<Servicio>) servicioService.findByTecnico(tecnicoRepository.findById(idTecnico));


        for (Servicio s : serviciosTecnico) {
            calendarDiaIni.setTime(new Date(s.getFechaInicial().getYear(),s.getFechaInicial().getDayOfMonth(),s.getFechaInicial().getMonthValue()));
            calendarDiaFin.setTime(new Date(s.getFechaFinal().getYear(),s.getFechaFinal().getDayOfMonth(),s.getFechaFinal().getMonthValue()));
            if (calendarDiaIni.get(Calendar.WEEK_OF_YEAR) == numSemana + 1 && numSemana + 1 == calendarDiaFin.get(Calendar.WEEK_OF_YEAR)) {
                fechaFini = s.getFechaFinal();
                fechaIni = s.getFechaInicial();
                diaContador = fechaIni;
                contDias = (fechaFini.getDayOfMonth() - fechaIni.getDayOfMonth());
                if (contDias != 0) {
                    for (int i = 1; i <= contDias; ++i) {
                        if (i == 1) {

                            calcularTiemposEntreDias(fechaIni, 24, calendarDiaIni);
                            contHorasPorServicio = contHorasPorServicio + (24 - fechaIni.getHour());
                        }
                        if (i == contDias) {
                            calcularTiemposEntreDias(fechaFini, fechaFini.getHour(), calendarDiaFin);
                            contHorasPorServicio = contHorasPorServicio + fechaFini.getHour();
                        }
                        contHorasPorServicio = contHorasPorServicio + 24;
                    }
                } else {
                    calcularTimposElMismoDia(fechaIni, fechaFini, calendarDiaFin);

                }
            }

        }
        trabajosSemanaTecnico.setIdTecnico(idTecnico);
        trabajosSemanaTecnico.setHorasSemanaNormales(horasSemanaNormales);
        trabajosSemanaTecnico.setHorasNocturnas(horasNocturnas);
        trabajosSemanaTecnico.setHorasExtraNormales(horasExtraNormales);
        return trabajosSemanaTecnico;
    }

    public void calcularTiemposEntreDias(LocalDateTime fecha, int horaFin, Calendar fechaConsultar) {
        if (fechaConsultar.get(Calendar.DAY_OF_WEEK) != 1) {
            if (horaFin != 24) {
                horasSemanaNormales = horasSemanaNormales + ((fecha.getHour()) - (fecha.getHour() - 7));
                horasNocturnas = horasNocturnas + (fecha.getHour() - ((20 - fecha.getHour()) + fecha.getHour() - 7));
            } else {
                horasSemanaNormales = horasSemanaNormales + (20 - fecha.getHour());
                horasNocturnas = horasNocturnas + (24 - 20 - fecha.getHour());
            }
        }
        if (fechaConsultar.get(Calendar.DAY_OF_WEEK) == 1) {
            if (horaFin != 24) {
                horasDominicales = horasDominicales + fecha.getHour();
            } else {
                horasDominicales = horasDominicales + (24 - fecha.getHour());
            }

        }
        horasExtraNormales = horasExtraNormales + (horasDominicales - 48);
        horasExtraDominicales = horasExtraNormales - (48 - horasDominicales);
        horasDominicales = horasDominicales - horasExtraDominicales;
    }

    public void calcularTimposElMismoDia(LocalDateTime fecha1, LocalDateTime fecha2, Calendar calendarDiaI) {
        if (calendarDiaI.get(Calendar.DAY_OF_WEEK) != 7) {
            if (7 <= fecha1.getHour() && 20 >= fecha1.getHour() && 7 <= fecha2.getHour() && 20 >= fecha2.getHour()) {
                horasSemanaNormales = horasSemanaNormales + (fecha2.getHour() - fecha1.getHour());
            }
            if (7 > fecha1.getHour() && 7 <= fecha2.getHour() && 20 >= fecha2.getHour()) {
                horasSemanaNormales = horasSemanaNormales + (fecha2.getHour() - 7) + (7 - fecha1.getHour());
            }
            if (7 > fecha1.getHour() && 20 < fecha2.getHour()) {
                horasSemanaNormales = horasSemanaNormales + 8;
                horasExtraNormales = (7 - fecha1.getHour()) + (24 - fecha2.getHour());
            }


            if (7 <= fecha1.getHour() && 20 >= fecha1.getHour() && 7 <= fecha2.getHour() && 20 >= fecha2.getHour()) {
                horasSemanaNormales = horasSemanaNormales + (20 - fecha1.getHour());
                horasExtraNormales = horasExtraNormales + (24 - fecha2.getHour());
            }
        }
        if (calendarDiaI.get(Calendar.DAY_OF_WEEK) == 7) {
            horasDominicales = horasDominicales + fecha1.getHour() + fecha2.getHour();
        }
    }

}
