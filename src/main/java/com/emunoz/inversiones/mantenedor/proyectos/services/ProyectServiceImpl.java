package com.emunoz.inversiones.mantenedor.proyectos.services;

import com.emunoz.inversiones.mantenedor.proyectos.models.entity.ProyectEntity;
import com.emunoz.inversiones.mantenedor.proyectos.models.request.ProyectRequestDTO;
import com.emunoz.inversiones.mantenedor.proyectos.models.response.ProyectDataResponseDTO;
import com.emunoz.inversiones.mantenedor.proyectos.models.response.ProyectResponseDTO;
import com.emunoz.inversiones.mantenedor.proyectos.proyectMapper.ProyectMapper;
import com.emunoz.inversiones.mantenedor.proyectos.repository.ProyectRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Log4j2
public class ProyectServiceImpl implements ProyectService {


    @Autowired
    private ProyectRepository proyectRepository;

    @Override
    public ProyectResponseDTO getAllProyects() {
        ProyectResponseDTO projectResponse = new ProyectResponseDTO();

        List<ProyectEntity> proyectEntities = proyectRepository.findAll();
        List<ProyectDataResponseDTO> proyectDataResponseDTOs = proyectEntities.stream()
                .map(ProyectMapper::toResponseDTO)
                .collect(Collectors.toList());


        if (proyectEntities.isEmpty()) {
            projectResponse.setMessage("No hay proyectos registrados.");
            projectResponse.setCode(1);
            return projectResponse;
        }

        projectResponse.setMessage("Proyectos encontrados.");
        projectResponse.setData(proyectDataResponseDTOs);
        projectResponse.setCode(2);

        return projectResponse;
    }

    @Override
    public ProyectResponseDTO getproyectById(Long id) {
        ProyectResponseDTO projectResponse = new ProyectResponseDTO();

        Optional<ProyectEntity> proyectOptional = proyectRepository.findById(id);

        if (!proyectOptional.isPresent()) {
            projectResponse.setMessage("No existe el proyecto.");
            projectResponse.setCode(1);
            return projectResponse;
        }

        ProyectEntity proyectEntity = proyectOptional.get();
        ProyectDataResponseDTO proyectDataResponseDTO = ProyectMapper.toResponseDTO(proyectEntity);

        projectResponse.setMessage("Proyecto encontrado.");
        projectResponse.setData(proyectDataResponseDTO);
        projectResponse.setCode(2);

        return projectResponse;

    }

    @Override
    public ProyectResponseDTO CreateProyect(ProyectRequestDTO proyectRequestDTO) {
        return null;
    }

    @Override
    public ProyectResponseDTO UpdateProyect(ProyectRequestDTO proyectRequestDTO) {
        return null;
    }

    @Override
    public ProyectResponseDTO deleteProyect(Long id) {
        return null;
    }
}
