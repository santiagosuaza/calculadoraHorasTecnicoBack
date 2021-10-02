package com.IAS.calculadoraTecnico.Domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

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
    private java.util.Date fechaInicial;
    private java.util.Date fechaFinal;
    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnico;


}
