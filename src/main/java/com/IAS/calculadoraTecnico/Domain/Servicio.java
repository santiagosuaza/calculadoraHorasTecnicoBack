package com.IAS.calculadoraTecnico.Domain;

import lombok.*;

import javax.persistence.*;
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
    private LocalDateTime fechaInicial;
    private LocalDateTime fechaFinal;
    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnico;


}
