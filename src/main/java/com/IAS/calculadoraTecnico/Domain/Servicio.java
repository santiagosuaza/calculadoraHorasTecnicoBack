package com.IAS.calculadoraTecnico.Domain;

import javax.validation.constraints.NotNull;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "tbl_servicio")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "la fecha iniciol no puede ser Null")
    private java.util.Date fechaInicial;
    @NotNull(message = "la fecha final no puede ser Null")
    private java.util.Date fechaFinal;
    @NotNull(message = "la cedula no puede ser Null")
    @Column(name = "cedula")
    private Long cedula;
    @NotNull(message = "la identificacion del servicio no puede ser Null")
    private String identificacionServicio;

    @ManyToOne
    @JoinColumn(name = "cedula", insertable = false,updatable = false)
    private Tecnico tecnico;


}
