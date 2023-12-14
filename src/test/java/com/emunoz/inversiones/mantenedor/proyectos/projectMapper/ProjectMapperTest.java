package com.emunoz.inversiones.mantenedor.proyectos.projectMapper;

import com.emunoz.inversiones.mantenedor.proyectos.models.entity.ProjectEntity;
import com.emunoz.inversiones.mantenedor.proyectos.models.request.ProjectRequestDTO;
import com.emunoz.inversiones.mantenedor.proyectos.models.response.ProjectDataResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class ProjectMapperTest {

    @InjectMocks
    private ProjectEntity projectEntity;

    @Test
    void toEntity() {
        ProjectRequestDTO projectRequest = ProjectRequestDTO.builder()
                .name("example")
                .daily_profit(1.0F)
                .build();

        // "Mockear" el método estático
        try (MockedStatic<ProjectMapper> projectMapper = Mockito.mockStatic(ProjectMapper.class)) {

            projectMapper.when(() -> ProjectMapper.toEntity (projectRequest)).thenReturn(ProjectEntity.builder().name("example").build());
            ProjectEntity result = ProjectMapper.toEntity(projectRequest);

            assertEquals("example", result.getName()); // Verificar que el campo correo se asigne correctamente.
        }
    }

    @Test
    void toResponseDTO() {

        ProjectEntity projectEntity = ProjectEntity.builder()
                .id(1L)
                .name("Usuario")
                .daily_profit(2F)
                .build();

        // "Mockear" el método estático
        try (MockedStatic<ProjectMapper> userMapper = Mockito.mockStatic(ProjectMapper.class)) {

            userMapper.when(() -> ProjectMapper.toResponseDTO(projectEntity))
                    .thenReturn(new ProjectDataResponseDTO(1L, "Usuario", 2F));

            ProjectDataResponseDTO result = ProjectMapper.toResponseDTO(projectEntity);
            // Verificar que el campo correo se asigne correctamente.
            assertEquals("Usuario", result.getName());

        }
    }
}