package com.emunoz.inversiones.mantenedor.proyectos.validation;

import com.emunoz.inversiones.mantenedor.proyectos.models.response.ProyectResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

@Component
public class ValidationUtils {

    public ResponseEntity<ProyectResponseDTO> handleValidationErrors(BindingResult bindingResult) {
        ProyectResponseDTO proyectResponseDTO = new ProyectResponseDTO();

        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.add(error.getDefaultMessage());
            }

            for (ObjectError error : bindingResult.getGlobalErrors()) {
                errors.add(error.getDefaultMessage());
            }
            proyectResponseDTO.setMessage("Campos vacios");
            proyectResponseDTO.setData(errors);
            proyectResponseDTO.setCode(0);
            return new ResponseEntity<>(proyectResponseDTO, HttpStatus.BAD_REQUEST);
        }

        return null; // No hay errores de validación
    }

}
