package com.emunoz.inversiones.mantenedor.proyectos.controllers;

import com.emunoz.inversiones.mantenedor.proyectos.models.entity.ProjectEntity;
import com.emunoz.inversiones.mantenedor.proyectos.models.request.ProjectRequestDTO;
import com.emunoz.inversiones.mantenedor.proyectos.models.response.ProjectResponseDTO;
import com.emunoz.inversiones.mantenedor.proyectos.repository.ProjectRepository;
import com.emunoz.inversiones.mantenedor.proyectos.services.ProjectService;
import com.emunoz.inversiones.mantenedor.proyectos.services.ValidationTokenService;
import com.emunoz.inversiones.mantenedor.proyectos.validation.ValidationUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProjectRepository projectRepository;

    @MockBean
    private ProjectService projectService;

    @MockBean
    private ValidationUtils validationUtils;

    @MockBean
    private ValidationTokenService validationTokenService;



    @Test
    void getAllProject_NotFound() throws Exception {
        ProjectResponseDTO projectResponseDTO = ProjectResponseDTO.builder()
                .message("No hay proyectos registrados.")
                .code(1)
                .build();

        when(projectService.getAllProjects()).thenReturn(projectResponseDTO);

        // Realiza la solicitud HTTP simulada sin un token válido
        mockMvc.perform(get("/api/V1/project"))
                .andExpect(jsonPath("$.message").value("No hay proyectos registrados."))
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllProject_Success() throws Exception {
        ProjectResponseDTO projectResponseDTO = ProjectResponseDTO.builder()
                .message("Proyectos encontrados.")
                .code(2)
                .build();

        when(projectService.getAllProjects()).thenReturn(projectResponseDTO);

        // Realiza la solicitud HTTP simulada sin un token válido
        mockMvc.perform(get("/api/V1/project"))
                .andExpect(jsonPath("$.message").value("Proyectos encontrados."))
                .andExpect(jsonPath("$.code").value(2))
                .andExpect(status().isOk());
    }


    @Test
    void getProjectById_NotFound() throws Exception {

        Long projectId = 1L;
        ProjectResponseDTO projectResponse = ProjectResponseDTO.builder()
                .message("No existe el proyecto.")
                .code(1)
                .build();

        when(projectService.getprojectById(projectId)).thenReturn(projectResponse);

        mockMvc.perform(get("/api/V1/project/{projectId}", projectId))
                .andExpect(jsonPath("$.message").value("No existe el proyecto."))
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(status().isNotFound());
    }

    @Test
    void getProjectById_Success() throws Exception {
        Long projectId = 1L;
        ProjectResponseDTO projectResponse = ProjectResponseDTO.builder()
                .message("Proyecto encontrado.")
                .code(2)
                .build();

        when(projectService.getprojectById(projectId)).thenReturn(projectResponse);

        mockMvc.perform(get("/api/V1/project/{projectId}", projectId))
                .andExpect(jsonPath("$.message").value("Proyecto encontrado."))
                .andExpect(jsonPath("$.code").value(2))
                .andExpect(status().isOk());
    }

    @Test
    void createUser_Unauthorized() throws Exception {
        ProjectRequestDTO projectRequest = ProjectRequestDTO.builder()
                .name("example")
                .daily_profit(1F)
                .build();

        ProjectResponseDTO projectResponse = ProjectResponseDTO.builder()
                .message("Usuario no válido.")
                .code(1)
                .build();

        when(validationTokenService.validateToken("0")).thenReturn(false);
        when(projectService.createProject(projectRequest)).thenReturn(projectResponse);

        mockMvc.perform(post("/api/V1/project").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(projectRequest)).header("Authorization","0"))
                .andExpect(jsonPath("$.message").value("Usuario no válido."))
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(status().isUnauthorized());

    }

    @Test
    void createUser_ProjectAlreadyExist() throws Exception {
        ProjectRequestDTO projectRequest = ProjectRequestDTO.builder()
                .name("example")
                .daily_profit(1F)
                .build();

        ProjectResponseDTO projectResponse = ProjectResponseDTO.builder()
                .message("El proyecto ya existe.")
                .code(1)
                .build();

        when(validationTokenService.validateToken("0")).thenReturn(true);
        when(projectService.createProject(projectRequest)).thenReturn(projectResponse);

        mockMvc.perform(post("/api/V1/project").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(projectRequest)).header("Authorization","0"))
                .andExpect(jsonPath("$.message").value("El proyecto ya existe."))
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(status().isConflict());

    }

    @Test
    void createUser_Success() throws Exception {
        ProjectRequestDTO projectRequest = ProjectRequestDTO.builder()
                .name("example")
                .daily_profit(1F)
                .build();

        ProjectResponseDTO projectResponse = ProjectResponseDTO.builder()
                .message("Proyecto creado con éxito.")
                .code(2)
                .build();

        when(validationTokenService.validateToken("0")).thenReturn(true);
        when(projectService.createProject(projectRequest)).thenReturn(projectResponse);

        mockMvc.perform(post("/api/V1/project").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(projectRequest)).header("Authorization","0"))
                .andExpect(jsonPath("$.message").value("Proyecto creado con éxito."))
                .andExpect(jsonPath("$.code").value(2))
                .andExpect(status().isOk());
    }

    @Test
    void updateUser_Unauthorized() throws Exception {
        ProjectRequestDTO projectRequest = ProjectRequestDTO.builder()
                .id(1L)
                .name("example")
                .daily_profit(2F)
                .build();

        ProjectResponseDTO projectResponse = ProjectResponseDTO.builder()
                .message("Usuario no válido.")
                .code(1)
                .build();

        when(projectService.updateProject(projectRequest)).thenReturn(projectResponse);
        when(validationTokenService.validateToken("0")).thenReturn(false);

        // Realiza la solicitud HTTP simulada sin un token válido
        mockMvc.perform(put("/api/V1/project").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(projectRequest)).header("Authorization","0"))
                .andExpect(jsonPath("$.message").value("Usuario no válido."))
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void updateUser_NotFound() throws Exception {
        ProjectRequestDTO projectRequest = ProjectRequestDTO.builder()
                .id(1L)
                .name("example")
                .daily_profit(2F)
                .build();

        ProjectResponseDTO projectResponse = ProjectResponseDTO.builder()
                .message("El proyecto no existe.")
                .code(0)
                .build();

        when(validationTokenService.validateToken("0")).thenReturn(true);
        when(projectRepository.findById(projectRequest.getId())).thenReturn(Optional.empty());
        when(projectService.updateProject(projectRequest)).thenReturn(projectResponse);

        // Realiza la solicitud HTTP simulada sin un token válido
        mockMvc.perform(put("/api/V1/project").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(projectRequest)).header("Authorization","0"))
                .andExpect(jsonPath("$.message").value("El proyecto no existe."))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUser_ProjectAlreadyExist() throws Exception {
        ProjectRequestDTO projectRequest = ProjectRequestDTO.builder()
                .id(1L)
                .name("example")
                .daily_profit(2F)
                .build();

        ProjectResponseDTO projectResponse = ProjectResponseDTO.builder()
                .message("El nombre ya existe en otro proyecto.")
                .code(1)
                .build();

        when(validationTokenService.validateToken("0")).thenReturn(true);
        when(projectService.updateProject(projectRequest)).thenReturn(projectResponse);
        when(projectRepository.findProyectByName(projectRequest.getName())).thenReturn(Optional.of(new ProjectEntity()));

        // Realiza la solicitud HTTP simulada sin un token válido
        mockMvc.perform(put("/api/V1/project").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(projectRequest)).header("Authorization","0"))
                .andExpect(jsonPath("$.message").value("El nombre ya existe en otro proyecto."))
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(status().isConflict());
    }


    @Test
    void updateUser_Success() throws Exception {
        ProjectRequestDTO projectRequest = ProjectRequestDTO.builder()
                .id(1L)
                .name("example")
                .daily_profit(2F)
                .build();

        ProjectResponseDTO projectResponse = ProjectResponseDTO.builder()
                .message("Proyecto actualizado con exito.")
                .code(2)
                .build();
        when(validationTokenService.validateToken("0")).thenReturn(true);
        when(projectService.updateProject(projectRequest)).thenReturn(projectResponse);
        when(projectRepository.findProyectByName(projectRequest.getName())).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/V1/project").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(projectRequest)).header("Authorization","0"))
                .andExpect(jsonPath("$.message").value("Proyecto actualizado con exito."))
                .andExpect(jsonPath("$.code").value(2))
                .andExpect(status().isOk());
    }


    @Test
    void deleteProject_Unauthorized() throws Exception {
        Long projectId = 1L;

        when(validationTokenService.validateToken("0")).thenReturn(false);

        mockMvc.perform(delete("/api/V1/project/{projectId}", projectId).header("Authorization","0"))
                .andExpect(jsonPath("$.message").value("Usuario no válido."))
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteProject_NotFound() throws Exception {

        Long projectId = 1L;
        ProjectResponseDTO projectResponse = ProjectResponseDTO.builder()
                .message("No existe el proyecto.")
                .code(1)
                .build();

        when(validationTokenService.validateToken("0")).thenReturn(true);
        when(projectService.deleteProject(projectId)).thenReturn(projectResponse);

        mockMvc.perform(delete("/api/V1/project/{projectId}", projectId).header("Authorization","0"))
                .andExpect(jsonPath("$.message").value("No existe el proyecto."))
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(status().isConflict());

    }

    @Test
    void deleteProject_Success() throws Exception {
        Long userId = 1L;
        ProjectResponseDTO projectResponse = ProjectResponseDTO.builder()
                .message("Proyecto eliminado con exito.")
                .code(2)
                .build();

        when(validationTokenService.validateToken("0")).thenReturn(true);
        when(projectService.deleteProject(userId)).thenReturn(projectResponse);

        mockMvc.perform(delete("/api/V1/project/{projectId}", userId).header("Authorization","0"))
                .andExpect(jsonPath("$.message").value("Proyecto eliminado con exito."))
                .andExpect(jsonPath("$.code").value(2))
                .andExpect(status().isOk());
    }
}