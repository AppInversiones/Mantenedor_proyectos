package com.emunoz.inversiones.mantenedor.proyectos.services;

import com.emunoz.inversiones.mantenedor.proyectos.models.entity.ProjectEntity;
import com.emunoz.inversiones.mantenedor.proyectos.models.request.ProjectRequestDTO;
import com.emunoz.inversiones.mantenedor.proyectos.models.response.ProjectResponseDTO;
import com.emunoz.inversiones.mantenedor.proyectos.repository.ProjectRepository;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Log4j2
@ExtendWith(MockitoExtension.class)
public class ProjectServiceImplTest {

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Test
    public void getAllProjects_ProjectNotFound() {

        when(projectRepository.findAll()).thenReturn(Lists.newArrayList());
        ProjectResponseDTO rsp = projectService.getAllProjects();

        assertEquals("No hay proyectos registrados.", rsp.getMessage());
        assertEquals(rsp.getCode(), 1);
    }

    @Test
    public void getAllProjects_ProjectFound() {

        when(projectRepository.findAll()).thenReturn(Lists.newArrayList(ProjectEntity.builder().build()));
        ProjectResponseDTO rsp = projectService.getAllProjects();

        assertEquals("Proyectos encontrados.", rsp.getMessage());
        assertEquals(rsp.getCode(), 2);
    }

    @Test
    public void getprojectById_ProjectNotFound() {

        long projectId = 1L;
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());
        ProjectResponseDTO rsp = projectService.getprojectById(projectId);

        assertEquals("No existe el proyecto.", rsp.getMessage());
        assertEquals(rsp.getCode(), 1);
    }

    @Test
    public void getprojectById_ProjectFound() {
        long projectId = 1L;
        ProjectEntity projectEntity = ProjectEntity.builder()
                .id(projectId)
                .name("example")
                .daily_profit(3.5F)
                .build();
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(projectEntity));
        ProjectResponseDTO rsp = projectService.getprojectById(projectId);

        assertEquals("Proyecto encontrado.", rsp.getMessage());
        assertEquals(rsp.getCode(), 2);
    }

    @Test
    public void createProject_projectAlreadyExists() {
        String nameProject = "project example";
        ProjectRequestDTO projectRequest = ProjectRequestDTO.builder()
                .name(nameProject)
                .daily_profit(3.0F)
                .build();

        when(projectRepository.findProyectByName(nameProject)).thenReturn(Optional.of(new ProjectEntity()));
        ProjectResponseDTO rsp = projectService.createProject(projectRequest);

        assertEquals("El proyecto ya existe.", rsp.getMessage());
        assertEquals(1, rsp.getCode());

    }

    @Test
    public void createProject_Success() {
        String nameProject = "project example";
        ProjectRequestDTO projectRequest = ProjectRequestDTO.builder()
                .name(nameProject)
                .daily_profit(3.0F)
                .build();

        when(projectRepository.findProyectByName(nameProject)).thenReturn(Optional.empty());
        when(projectRepository.save(any(ProjectEntity.class))).thenAnswer(invocation -> {
            ProjectEntity projectToSave = invocation.getArgument(0);
            projectToSave.setId(1L);
            return projectToSave;
        });
        ProjectResponseDTO rsp = projectService.createProject(projectRequest);

        assertEquals("Proyecto creado con éxito.", rsp.getMessage());
        assertEquals(2, rsp.getCode());
    }


    @Test
    public void updateProject_projectNotFound() {

        Long projectId = 1L;
        ProjectRequestDTO projectRequest = ProjectRequestDTO.builder()
                .id(projectId)
                .name("projectExample")
                .daily_profit(3.5F)
                .build();

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());
        ProjectResponseDTO rsp = projectService.updateProject(projectRequest);

        assertEquals("El proyecto no existe.", rsp.getMessage());
        assertEquals(0, rsp.getCode());

    }

    @Test
    public void updateProject_projectNameAlreadyExist() {

        Long projectId = 1L;
        String projectName = "projectExample";
        ProjectRequestDTO projectRequest = ProjectRequestDTO.builder()
                .id(projectId)
                .name(projectName)
                .daily_profit(3.5F)
                .build();

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(new ProjectEntity()));
        when(projectRepository.findProyectByName(projectName)).thenReturn(Optional.of(new ProjectEntity()));
        ProjectResponseDTO rsp = projectService.updateProject(projectRequest);

        assertEquals("El nombre ya existe en otro proyecto.", rsp.getMessage());
        assertEquals(1, rsp.getCode());

    }

    @Test
    public void updateProject_Success() {

        Long projectId = 1L;
        String projectName = "projectExample";
        ProjectRequestDTO projectRequest = ProjectRequestDTO.builder()
                .id(projectId)
                .name(projectName)
                .daily_profit(3.5F)
                .build();

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(new ProjectEntity()));
        when(projectRepository.findProyectByName(projectName)).thenReturn(Optional.empty());
        ProjectResponseDTO rsp = projectService.updateProject(projectRequest);

        assertEquals("Proyecto actualizado con exito.", rsp.getMessage());
        assertEquals(2, rsp.getCode());

    }

    @Test
    public void deleteProject_ProjectNotFound() {
        Long projectId = 1L;

        when(projectRepository.existsById(projectId)).thenReturn(false);
        ProjectResponseDTO rsp = projectService.deleteProject(projectId);

        assertEquals("No existe el projecto.", rsp.getMessage());
        assertEquals(1, rsp.getCode());
    }

    @Test
    public void deleteProject_Success() {
        Long projectId = 1L;

        when(projectRepository.existsById(projectId)).thenReturn(true);

        ProjectResponseDTO rsp = projectService.deleteProject(projectId);

        assertEquals("Proyecto eliminado con exito.", rsp.getMessage());
        assertEquals(2, rsp.getCode());
    }
}