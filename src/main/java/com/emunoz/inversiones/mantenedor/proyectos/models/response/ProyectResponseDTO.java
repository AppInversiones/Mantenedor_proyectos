package com.emunoz.inversiones.mantenedor.proyectos.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProyectResponseDTO {

    private String  message;
    private Object data;
    private Integer code;
}
