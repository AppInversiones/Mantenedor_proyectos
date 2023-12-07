package com.emunoz.inversiones.mantenedor.proyectos.controllers;

import com.emunoz.inversiones.mantenedor.proyectos.models.response.ProyectResponseDTO;
import com.emunoz.inversiones.mantenedor.proyectos.services.ProyectService;
import com.emunoz.inversiones.mantenedor.proyectos.validation.ValidationUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping(path = "api/V1/proyecto")
public class ProyectController {

    @Autowired
    private ProyectService proyectService;

    @Autowired
    private ValidationUtils validationUtils;



    @Operation(summary = "Servicio que lista los proyectos.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Proyectos encontrados.", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))
                    }),
                    @ApiResponse(responseCode = "204", description = "No se encontraron proyectos.", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.", content = @Content),
            }
    )
    @GetMapping
    public ResponseEntity<ProyectResponseDTO> getAllProyect(){

        ProyectResponseDTO res =  proyectService.getAllProyects();
        log.error(res);

        if (res.getCode() == 1){
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        } else if (res.getCode() == 2){
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Servicio que lista un producto.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Producto encontrado.", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))
                    }),
                    @ApiResponse(responseCode = "204", description = "No se encontro el producto.", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Error de autorización.", content = @Content),
            }
    )
    @GetMapping (path = "{proyectId}")
    public ResponseEntity<ProyectResponseDTO> getProyectById(@PathVariable("proyectId") Long id) {


        ProyectResponseDTO res = proyectService.getproyectById(id);

        if (res.getCode() == 1){
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        } else if (res.getCode() == 2){
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
