package com.emunoz.inversiones.mantenedor.proyectos.validation;

import com.emunoz.inversiones.mantenedor.proyectos.models.response.ProjectResponseDTO;
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

    public ResponseEntity<ProjectResponseDTO> handleValidationErrors(BindingResult bindingResult) {
        ProjectResponseDTO projectResponseDTO = new ProjectResponseDTO();

        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.add(error.getDefaultMessage());
            }

            for (ObjectError error : bindingResult.getGlobalErrors()) {
                errors.add(error.getDefaultMessage());
            }
            projectResponseDTO.setMessage("Campos vacios");
            projectResponseDTO.setData(errors);
            projectResponseDTO.setCode(0);
            return new ResponseEntity<>(projectResponseDTO, HttpStatus.BAD_REQUEST);
        }

        return null; // No hay errores de validación
    }

}
