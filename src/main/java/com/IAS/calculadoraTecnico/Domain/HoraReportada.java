package com.IAS.calculadoraTecnico.Domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_horas_reportadas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HoraReportada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date fechaInicio;
    private Date fechaFinal;

    @ManyToOne
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;


}
