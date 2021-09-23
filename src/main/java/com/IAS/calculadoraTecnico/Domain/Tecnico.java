package com.IAS.calculadoraTecnico.Domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_tecnico")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tecnico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Long cedula;
    private String direccion;
    private Double horaValor;


    @JsonManagedReference
    @OneToMany(mappedBy = "tecnico", orphanRemoval = true)
    private List<Servicio> servicios =new ArrayList<>();


}
