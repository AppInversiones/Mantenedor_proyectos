package com.emunoz.inversiones.mantenedor.proyectos.proyectMapper;

import com.emunoz.inversiones.mantenedor.proyectos.models.entity.ProyectEntity;
import com.emunoz.inversiones.mantenedor.proyectos.models.request.ProyectRequestDTO;
import com.emunoz.inversiones.mantenedor.proyectos.models.response.ProyectDataResponseDTO;
import com.emunoz.inversiones.mantenedor.proyectos.models.response.ProyectResponseDTO;

public class ProyectMapper {

    public static ProyectEntity toEntity(ProyectRequestDTO proyectRequestDTO) {
        ProyectEntity proyectEntity = new ProyectEntity();
        proyectEntity.setName(proyectRequestDTO.getName());
        proyectEntity.setDaily_profit(proyectRequestDTO.getDaily_profit());

        return proyectEntity;
    }

    public static ProyectDataResponseDTO toResponseDTO(ProyectEntity proyectEntity) {
        ProyectDataResponseDTO proyectdataResponseDTO = new ProyectDataResponseDTO();
        proyectdataResponseDTO.setId(proyectEntity.getId());
        proyectdataResponseDTO.setName(proyectEntity.getName());
        proyectdataResponseDTO.setDaily_profit(proyectEntity.getDaily_profit());

        return proyectdataResponseDTO;
    }
}
