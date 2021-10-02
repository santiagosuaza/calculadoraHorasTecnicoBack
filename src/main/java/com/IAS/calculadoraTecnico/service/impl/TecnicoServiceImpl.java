package com.IAS.calculadoraTecnico.service.impl;

import com.IAS.calculadoraTecnico.Domain.Servicio;
import com.IAS.calculadoraTecnico.Domain.Tecnico;
import com.IAS.calculadoraTecnico.Domain.TrabajosSemanaTecnico;
import com.IAS.calculadoraTecnico.repository.TecnicoRepository;
import com.IAS.calculadoraTecnico.service.ServicioService;
import com.IAS.calculadoraTecnico.service.TecnicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    TrabajosSemanaTecnico trabajosSemanaTecnico = new TrabajosSemanaTecnico();
    int horasSemanaNormales;
    int horasDominicales;
    int horasNocturnas;
    int horasExtraNormales;
    int horasExtraDominicales;
    boolean flag = true;
    int maxHor = 48;
    int contHorasPorServicio = 0;

    Calendar calendarDiaIni = Calendar.getInstance();
    ;
    Calendar calendarDiaFin = Calendar.getInstance();
    ;

    Calendar calendarDiaCont = Calendar.getInstance();
    Date diaContador;
    Date fechaIni;
    Date fechaFini;
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
        horasSemanaNormales = 0;
        horasDominicales = 0;
        horasNocturnas = 0;
        horasExtraNormales = 0;
        horasExtraDominicales = 0;
        ArrayList<Servicio> serviciosTecnico = new ArrayList<Servicio>();
        serviciosTecnico = (ArrayList<Servicio>) servicioService.findByTecnico(tecnicoRepository.findById(idTecnico));


        for (Servicio s : serviciosTecnico) {
            calendarDiaIni.setTime(s.getFechaInicial());
            calendarDiaFin.setTime(s.getFechaFinal());
            if (calendarDiaIni.get(Calendar.WEEK_OF_YEAR) == numSemana && numSemana == calendarDiaFin.get(Calendar.WEEK_OF_YEAR)) {
                fechaFini = s.getFechaFinal();
                fechaIni = s.getFechaInicial();
                diaContador = fechaIni;
                contDias = (fechaFini.getDay() - fechaIni.getDay());
                if (contDias != 0) {
                    for (int i = 1; i <= contDias; ++i) {
                        if (i == 1) {

                            calcularTiemposEntreDias(fechaIni, 24, calendarDiaIni);
                            contHorasPorServicio = contHorasPorServicio + (24 - fechaIni.getHours());
                        }
                        if (i == contDias) {
                            calcularTiemposEntreDias(fechaFini, fechaFini.getHours(), calendarDiaFin);
                            contHorasPorServicio = contHorasPorServicio + fechaFini.getHours();
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

    public void calcularTiemposEntreDias(Date fecha, int horaFin, Calendar fechaConsultar) {
        if (fechaConsultar.get(Calendar.DAY_OF_WEEK) != 1) {
            if (horaFin != 24) {

                if (fecha.getHours() > 7){
                    horasNocturnas = horasNocturnas + 7;
                horasSemanaNormales = horasSemanaNormales + (fecha.getHours() - 7);
            } else {
                    horasNocturnas = horasNocturnas+fecha.getHours();
                }

            } else {
                horasSemanaNormales = horasSemanaNormales + (20-fecha.getHours());
                horasNocturnas = horasNocturnas + (24 - 20 - fecha.getHours());
            }
        } else {
            if (horaFin != 24) {
                horasDominicales = horasDominicales + fecha.getHours();
            } else {
                horasDominicales = horasDominicales + (24 - fecha.getHours());
            }

        }
        horasExtraNormales = horasExtraNormales + (horasDominicales - 48);
        horasExtraDominicales = horasExtraNormales - (48 - horasDominicales);
        horasDominicales = horasDominicales - horasExtraDominicales;
    }

    public void calcularTimposElMismoDia(Date fecha1, Date fecha2, Calendar calendarDiaI) {
        if (calendarDiaI.get(Calendar.DAY_OF_WEEK) != 7) {
            if (7 <= fecha1.getHours() && 20 >= fecha1.getHours() && 7 <= fecha2.getHours() && 20 >= fecha2.getHours()) {
                horasSemanaNormales = horasSemanaNormales + (fecha2.getHours() - fecha1.getHours());
            }
            if (7 > fecha1.getHours() && 7 <= fecha2.getHours() && 20 >= fecha2.getHours()) {
                horasSemanaNormales = horasSemanaNormales + (fecha2.getHours() - 7) + (7 - fecha1.getHours());
            }
            if (7 > fecha1.getHours() && 20 < fecha2.getHours()) {
                horasSemanaNormales = horasSemanaNormales + 8;
                horasExtraNormales = (7 - fecha1.getHours()) + (24 - fecha2.getHours());
            }


            if (7 <= fecha1.getHours() && 20 >= fecha1.getHours() && 7 <= fecha2.getHours() && 20 >= fecha2.getHours()) {
                horasSemanaNormales = horasSemanaNormales + (20 - fecha1.getHours());
                horasExtraNormales = horasExtraNormales + (24 - fecha2.getHours());
            }
        }
        if (calendarDiaI.get(Calendar.DAY_OF_WEEK) == 7) {
            horasDominicales = horasDominicales + fecha1.getHours() + fecha2.getHours();
        }
    }

}
