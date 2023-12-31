package com.emunoz.inversiones.mantenedor.proyectos.repository;

import com.emunoz.inversiones.mantenedor.proyectos.models.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    Optional<ProjectEntity> findProyectByName(String name);

    Optional<ProjectEntity> findById(Long id);
}
