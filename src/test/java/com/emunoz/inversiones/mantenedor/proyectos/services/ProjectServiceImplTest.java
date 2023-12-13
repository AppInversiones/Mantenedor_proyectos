package com.emunoz.inversiones.mantenedor.proyectos.services;

import com.emunoz.inversiones.mantenedor.proyectos.models.entity.ProjectEntity;
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

        log.error(rsp + "RSP");

        assertEquals("No hay proyectos registrados.", rsp.getMessage());
        assertEquals(rsp.getCode(), 1);
    }

    @Test
    void getAllProjects_ProjectFound() {

        when(projectRepository.findAll()).thenReturn(Lists.newArrayList(ProjectEntity.builder().build()));
        ProjectResponseDTO rsp = projectService.getAllProjects();

        assertEquals("Proyectos encontrados.", rsp.getMessage());
        assertEquals(rsp.getCode(), 2);
    }

    @Test
    void getprojectById_ProjectFound() {
        long projectId = 1L;
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());
        ProjectResponseDTO rsp = projectService.getprojectById(projectId);
        // Asegura que el usuario no existe.
        assertEquals("No existe el proyecto.", rsp.getMessage());
        assertEquals(rsp.getCode(), 1);
    }

    @Test
    void createProject_ProjectFound() {
        long projectId = 1L;
        ProjectEntity projectEntity = ProjectEntity.builder()
                .id(projectId)
                .name("example")
                .daily_profit(3.5F)
                .build();
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(projectEntity));
        ProjectResponseDTO rsp = projectService.getprojectById(projectId);
        // Asegura que el usuario no existe.
        assertEquals("Proyecto encontrado.", rsp.getMessage());
        assertEquals(rsp.getCode(), 2);
    }

    @Test
    void updateProject() {
    }

    @Test
    void deleteProject() {
    }
}