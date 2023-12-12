package com.emunoz.inversiones.mantenedor.proyectos.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDataResponseDTO {

    private Long id;
    private String name;
    private Float daily_profit;
}
