package com.emunoz.inversiones.mantenedor.proyectos.services;

import com.emunoz.inversiones.mantenedor.proyectos.models.request.ProjectRequestDTO;
import com.emunoz.inversiones.mantenedor.proyectos.models.response.ProjectResponseDTO;

public interface ProjectService {
    ProjectResponseDTO getAllProjects();
    ProjectResponseDTO getprojectById(Long id);
    ProjectResponseDTO createProject(ProjectRequestDTO projectRequestDTO);
    ProjectResponseDTO updateProject(ProjectRequestDTO projectRequestDTO);
    ProjectResponseDTO deleteProject(Long id);
}
