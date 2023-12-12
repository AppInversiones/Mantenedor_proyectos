package com.emunoz.inversiones.mantenedor.proyectos.projectMapper;

import com.emunoz.inversiones.mantenedor.proyectos.models.entity.ProjectEntity;
import com.emunoz.inversiones.mantenedor.proyectos.models.request.ProjectRequestDTO;
import com.emunoz.inversiones.mantenedor.proyectos.models.response.ProjectDataResponseDTO;

public class ProjectMapper {

    public static ProjectEntity toEntity(ProjectRequestDTO projectRequestDTO) {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setName(projectRequestDTO.getName());
        projectEntity.setDaily_profit(projectRequestDTO.getDaily_profit());

        return projectEntity;
    }

    public static ProjectDataResponseDTO toResponseDTO(ProjectEntity projectEntity) {
        ProjectDataResponseDTO proyectdataResponseDTO = new ProjectDataResponseDTO();
        proyectdataResponseDTO.setId(projectEntity.getId());
        proyectdataResponseDTO.setName(projectEntity.getName());
        proyectdataResponseDTO.setDaily_profit(projectEntity.getDaily_profit());

        return proyectdataResponseDTO;
    }
}
