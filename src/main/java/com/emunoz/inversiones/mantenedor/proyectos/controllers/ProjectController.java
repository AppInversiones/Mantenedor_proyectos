package com.emunoz.inversiones.mantenedor.proyectos.controllers;

import com.emunoz.inversiones.mantenedor.proyectos.models.request.ProjectRequestDTO;
import com.emunoz.inversiones.mantenedor.proyectos.models.response.ProjectResponseDTO;
import com.emunoz.inversiones.mantenedor.proyectos.services.ProjectService;
import com.emunoz.inversiones.mantenedor.proyectos.services.ValidationTokenService;
import com.emunoz.inversiones.mantenedor.proyectos.validation.ValidationUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping(path = "api/V1/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ValidationUtils validationUtils;

    @Autowired
    private ValidationTokenService validationTokenService;


    //-------------------
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
    public ResponseEntity<ProjectResponseDTO> getAllProject(){

        ProjectResponseDTO res =  projectService.getAllProjects();

        if (res.getCode() == 1){
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        } else if (res.getCode() == 2){
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //-------------------
    @Operation(summary = "Servicio que lista un proyecto por su id.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "proyecto encontrado.", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))
                    }),
                    @ApiResponse(responseCode = "204", description = "No se encontro el proyecto.", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Error de autorización.", content = @Content),
            }
    )
    @GetMapping (path = "{projectId}")
    public ResponseEntity<ProjectResponseDTO>getProjectById(@PathVariable("projectId") Long id) {

        ProjectResponseDTO res = projectService.getprojectById(id);

        if (res.getCode() == 1){
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        } else if (res.getCode() == 2){
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //-------------------
    @Operation(summary = "Agregar un nuevo proyecto")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "proyecto agregado con éxito", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Solicitud no válida", content = @Content),
                    @ApiResponse(responseCode = "409", description = "proyecto con el mismo nombre ya existe", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content),
            }
    )
    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createUser(@Validated @RequestBody ProjectRequestDTO projectRequest, BindingResult bindingResult, @RequestHeader(name = "Authorization") String token) {

        ResponseEntity<ProjectResponseDTO> validationError = validationUtils.handleValidationErrors(bindingResult);
        if (validationError != null) {
            return validationError;
        }

        if (!validationTokenService.validateToken(token)) {
            ProjectResponseDTO projectResponse = new ProjectResponseDTO();

            projectResponse.setMessage("Usuario no válido.");
            projectResponse.setCode(1);
            return new ResponseEntity<>(projectResponse, HttpStatus.UNAUTHORIZED);
        }

        ProjectResponseDTO res = projectService.createProject(projectRequest);

        if (res.getCode() == 1){
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        } else if (res.getCode() == 2){
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Editar un proyecto")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Proyecto actualizado con éxito", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Proyecto no encontrado", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content),
            }
    )
    @PutMapping
    public ResponseEntity<ProjectResponseDTO> updateUser(@Validated @RequestBody ProjectRequestDTO projectRequest, BindingResult bindingResult, @RequestHeader(name = "Authorization") String token){


        ResponseEntity<ProjectResponseDTO> validationError = validationUtils.handleValidationErrors(bindingResult);
        if (validationError != null) {
            return validationError;
        }

        if (!validationTokenService.validateToken(token)) {
            ProjectResponseDTO projectResponse = new ProjectResponseDTO();

            projectResponse.setMessage("Usuario no válido.");
            projectResponse.setCode(1);
            return new ResponseEntity<>(projectResponse, HttpStatus.UNAUTHORIZED);
        }

        ProjectResponseDTO res = projectService.updateProject(projectRequest);

        if (res.getCode() == 0) {
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        } else if (res.getCode() == 1) {
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        } else if (res.getCode() == 2){
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Eliminar un proyecto")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "202", description = "Proyecto eliminado con éxito", content = @Content),
                    @ApiResponse(responseCode = "409", description = "Proyecto no encontrado", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content),
            }
    )
    @DeleteMapping(path = "{projectId}")
    public ResponseEntity<ProjectResponseDTO> deleteProject(@PathVariable("projectId") Long projectId, @RequestHeader(name = "Authorization") String token) {

        if (!validationTokenService.validateToken(token)) {
            ProjectResponseDTO projectResponse = new ProjectResponseDTO();
            projectResponse.setMessage("Usuario no válido.");
            projectResponse.setCode(1);
            return new ResponseEntity<>(projectResponse, HttpStatus.UNAUTHORIZED);
        }

        ProjectResponseDTO res = projectService.deleteProject(projectId);

        if(res.getCode() == 1) {
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        } else if (res.getCode() == 2){
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
