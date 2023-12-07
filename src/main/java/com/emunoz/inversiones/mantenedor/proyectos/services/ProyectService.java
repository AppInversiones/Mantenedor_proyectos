package com.emunoz.inversiones.mantenedor.proyectos.services;

import com.emunoz.inversiones.mantenedor.proyectos.models.request.ProyectRequestDTO;
import com.emunoz.inversiones.mantenedor.proyectos.models.response.ProyectResponseDTO;

public interface ProyectService {
    ProyectResponseDTO  getAllProyects();
    ProyectResponseDTO getproyectById(Long id);
    ProyectResponseDTO CreateProyect(ProyectRequestDTO proyectRequestDTO);
    ProyectResponseDTO UpdateProyect(ProyectRequestDTO proyectRequestDTO);
    ProyectResponseDTO deleteProyect(Long id);
}
