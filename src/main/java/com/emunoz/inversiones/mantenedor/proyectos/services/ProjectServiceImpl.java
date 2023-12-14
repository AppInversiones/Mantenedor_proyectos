package com.emunoz.inversiones.mantenedor.proyectos.services;

import com.emunoz.inversiones.mantenedor.proyectos.models.entity.ProjectEntity;
import com.emunoz.inversiones.mantenedor.proyectos.models.request.ProjectRequestDTO;
import com.emunoz.inversiones.mantenedor.proyectos.models.response.ProjectDataResponseDTO;
import com.emunoz.inversiones.mantenedor.proyectos.models.response.ProjectResponseDTO;
import com.emunoz.inversiones.mantenedor.proyectos.projectMapper.ProjectMapper;
import com.emunoz.inversiones.mantenedor.proyectos.repository.ProjectRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Log4j2
public class ProjectServiceImpl implements ProjectService {


    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public ProjectResponseDTO getAllProjects() {
        ProjectResponseDTO projectResponse = new ProjectResponseDTO();

        List<ProjectEntity> proyectEntities = projectRepository.findAll();
        List<ProjectDataResponseDTO> projectDataResponseDTOS = proyectEntities.stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());


        if (proyectEntities.isEmpty()) {
            projectResponse.setMessage("No hay proyectos registrados.");
            projectResponse.setCode(1);
            return projectResponse;
        }

        projectResponse.setMessage("Proyectos encontrados.");
        projectResponse.setData(projectDataResponseDTOS);
        projectResponse.setCode(2);
        return projectResponse;
    }

    @Override
    public ProjectResponseDTO getprojectById(Long id) {
        ProjectResponseDTO projectResponse = new ProjectResponseDTO();

        Optional<ProjectEntity> proyectOptional = projectRepository.findById(id);

        if (!proyectOptional.isPresent()) {
            projectResponse.setMessage("No existe el proyecto.");
            projectResponse.setCode(1);
            return projectResponse;
        }

        ProjectEntity projectEntity = proyectOptional.get();
        ProjectDataResponseDTO projectDataResponseDTO = ProjectMapper.toResponseDTO(projectEntity);

        projectResponse.setMessage("Proyecto encontrado.");
        projectResponse.setData(projectDataResponseDTO);
        projectResponse.setCode(2);
        return projectResponse;

    }

    @Override
    public ProjectResponseDTO createProject(ProjectRequestDTO projectRequest) {

        ProjectResponseDTO projectResponse = new ProjectResponseDTO();

        Optional<ProjectEntity> existingProject = projectRepository.findProyectByName(projectRequest.getName());

        if (existingProject.isPresent()) {
            projectResponse.setMessage("El proyecto ya existe.");
            projectResponse.setCode(1);
            return projectResponse;
        }

        ProjectEntity newProjectEntity = ProjectMapper.toEntity(projectRequest);
        projectRepository.save(newProjectEntity);

        ProjectDataResponseDTO projectDataResponseDTO = ProjectMapper.toResponseDTO(newProjectEntity);

        projectResponse.setMessage("Proyecto creado con éxito.");
        projectResponse.setData(projectDataResponseDTO);
        projectResponse.setCode(2);

        return projectResponse;
    }

    @Override
    public ProjectResponseDTO updateProject(ProjectRequestDTO projectRequest) {
        ProjectResponseDTO projectResponse = new ProjectResponseDTO();

        Optional<ProjectEntity>  existingProject = projectRepository.findById(projectRequest.getId());

        if(!existingProject.isPresent()) {
            projectResponse.setMessage("El proyecto no existe.");
            projectResponse.setCode(0);
            return projectResponse;
        }

        ProjectEntity projectToUpdate = existingProject.get();

        if (projectRequest.getName() != null && !projectRequest.getName().equals(projectToUpdate.getName())) {
            Optional<ProjectEntity> projectWithNewName = projectRepository.findProyectByName(projectRequest.getName());
            if (projectWithNewName.isPresent()) {
                projectResponse.setMessage("El nombre ya existe en otro proyecto.");
                projectResponse.setCode(1);
                return projectResponse;
            }
        }

        if(projectRequest.getName() != null) {
            projectToUpdate.setName(projectRequest.getName());
        }
        if (projectRequest.getDaily_profit() != null) {
            projectToUpdate.setDaily_profit(projectRequest.getDaily_profit());
        }

        projectRepository.save(projectToUpdate);

        ProjectDataResponseDTO updateProjectResponse = ProjectMapper.toResponseDTO(projectToUpdate);

        projectResponse.setMessage("Proyecto actualizado con exito.");
        projectResponse.setData(updateProjectResponse);
        projectResponse.setCode(2);

        return projectResponse;
    }

    @Override
    public ProjectResponseDTO deleteProject(Long id) {
        ProjectResponseDTO projectResponse = new ProjectResponseDTO();

        boolean exist = projectRepository.existsById(id);

        if (!exist) {
            projectResponse.setMessage("No existe el proyecto.");
            projectResponse.setCode(1);
            return projectResponse;
        }

        projectRepository.deleteById(id);
        projectResponse.setMessage("Proyecto eliminado con exito.");
        projectResponse.setCode(2);
        return projectResponse;
    }
}
