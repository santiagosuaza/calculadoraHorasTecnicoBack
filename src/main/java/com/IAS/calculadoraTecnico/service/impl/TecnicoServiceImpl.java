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
                    for (int i = 0; i <= contDias; i++) {
                        if (i == 0) {
                            calcularTiemposEntreDias(fechaIni, false, calendarDiaIni);

                        }
                       else if (i == contDias) {
                            calcularTiemposEntreDias(fechaFini, true, calendarDiaFin);
                        } else {
                            horasSemanaNormales = horasSemanaNormales + 13;
                            horasNocturnas = horasNocturnas + 11;
                        }
                    }
                } else {
                    calcularTimposElMismoDia(fechaIni, fechaFini, calendarDiaFin);

                }
            }

        }
        if(contHorasPorServicio>48){
            horasExtraDominicales=48-horasDominicales;
            horasDominicales=48;
            horasExtraNormales=48-horasDominicales;
            horasDominicales=48;
        }
        trabajosSemanaTecnico.setIdTecnico(idTecnico);
        trabajosSemanaTecnico.setHorasSemanaNormales(horasSemanaNormales);
        trabajosSemanaTecnico.setHorasNocturnas(horasNocturnas);
        trabajosSemanaTecnico.setHorasExtraNormales(horasExtraNormales);
        return trabajosSemanaTecnico;
    }

    public void calcularTiemposEntreDias(Date fecha, boolean ultimoDia, Calendar fechaConsultar) {
        if (fechaConsultar.get(Calendar.DAY_OF_WEEK) != 1) {
            if (!ultimoDia) {
                if (fecha.getHours() >= 7) {
                    horasNocturnas = horasNocturnas + 4;
                    horasSemanaNormales = horasSemanaNormales + (20 - fecha.getHours());
                    contHorasPorServicio=contHorasPorServicio+horasNocturnas+horasSemanaNormales;
                } else {
                    horasNocturnas = horasNocturnas + (7 - fecha.getHours()) + 4;
                    horasSemanaNormales = horasSemanaNormales + 13;
                    contHorasPorServicio=contHorasPorServicio+horasNocturnas+horasSemanaNormales;
                }
            } else {
                if (fecha.getHours() < 7) {
                    horasNocturnas = horasNocturnas + fecha.getHours();
                    contHorasPorServicio=contHorasPorServicio+horasNocturnas;
                } else if(fecha.getHours() < 20){
                    horasNocturnas = horasNocturnas + 7;
                    horasSemanaNormales = horasSemanaNormales + (fecha.getHours()-7);
                    contHorasPorServicio=contHorasPorServicio+horasNocturnas+horasSemanaNormales;
                }else {
                    horasNocturnas = horasNocturnas + 7 + (24 - fecha.getHours());
                    horasSemanaNormales = horasSemanaNormales + 13;
                    contHorasPorServicio=contHorasPorServicio+horasNocturnas+horasSemanaNormales;
                }
            }
        } else {
            horasDominicales = horasDominicales + fecha.getHours();
            contHorasPorServicio=contHorasPorServicio+horasDominicales;
        }

        horasExtraNormales = horasExtraNormales + (horasDominicales - 48);
        horasExtraDominicales = horasExtraNormales - (48 - horasDominicales);
        horasDominicales = horasDominicales - horasExtraDominicales;
        contHorasPorServicio=contHorasPorServicio+horasNocturnas+horasSemanaNormales+horasDominicales;

    }

    public void calcularTimposElMismoDia(Date fechaIni, Date fechaFinal, Calendar calendarDiaI) {
        if (calendarDiaI.get(Calendar.DAY_OF_WEEK) != 1) {
            if (7 <= fechaIni.getHours() && 20 >= fechaIni.getHours() && 7 <= fechaFinal.getHours() && 20 >= fechaFinal.getHours()) {
                horasSemanaNormales = horasSemanaNormales + (fechaFinal.getHours() - fechaIni.getHours());
            }
            if (7 > fechaIni.getHours() && 7 <= fechaFinal.getHours() && 20 >= fechaFinal.getHours()) {
                horasSemanaNormales = horasSemanaNormales + (fechaFinal.getHours() - 7) + (7 - fechaIni.getHours());
                horasExtraNormales=horasExtraNormales+(7-fechaIni.getHours());
            }
            if (7 > fechaIni.getHours() && 20 < fechaFinal.getHours()) {
                horasSemanaNormales = horasSemanaNormales + 8;
                horasExtraNormales =  horasExtraNormales+(7 - fechaIni.getHours()) + (24 - fechaFinal.getHours());
            }


            if (7 <= fechaIni.getHours() && 20 >= fechaIni.getHours() &&  20 <fechaFinal.getHours()) {
                horasSemanaNormales = horasSemanaNormales + (20 - fechaIni.getHours());
                horasExtraNormales = horasExtraNormales + (24 - fechaFinal.getHours());
            }
        }else{
            horasDominicales = horasDominicales +( fechaFinal.getHours() - fechaIni.getHours());
        }
    }

}
