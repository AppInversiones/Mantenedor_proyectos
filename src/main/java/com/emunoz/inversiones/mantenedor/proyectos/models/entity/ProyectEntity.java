package com.emunoz.inversiones.mantenedor.proyectos.models.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "proyects")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProyectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "daily_profit")
    private Float daily_profit;

}
