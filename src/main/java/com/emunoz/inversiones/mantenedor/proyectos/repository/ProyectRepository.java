package com.emunoz.inversiones.mantenedor.proyectos.repository;

import com.emunoz.inversiones.mantenedor.proyectos.models.entity.ProyectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProyectRepository extends JpaRepository<ProyectEntity, Long> {

    Optional<ProyectEntity> findProyectByName(String name);

    Optional<ProyectEntity> findById(Long id);
}
