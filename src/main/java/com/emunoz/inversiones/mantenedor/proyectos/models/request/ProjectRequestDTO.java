package com.emunoz.inversiones.mantenedor.proyectos.models.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class ProjectRequestDTO {

    private Long id;

    @NotBlank(message = "El nombre no puede estar en blanco.")
    @Size(max = 30)
    private  String name;

    @NotNull(message = "La ganancia diaria no puede estar en blanco.")
    private Float daily_profit;
}
