package org.sena.saludcontigo.controllers;

import org.sena.saludcontigo.entities.ExamenMedico;
import org.sena.saludcontigo.services.IExamenMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200", "*"})
@RestController
@RequestMapping("/api")
public class ExamenMedicoRestController {

    @Autowired
    private IExamenMedicoService examenMedicoService;

    /**
     * Obtiene todos los exámenes médicos
     * @return Lista de exámenes médicos
     */
    @GetMapping("/examenes")
    @ResponseStatus(HttpStatus.OK)
    public List<ExamenMedico> index() {
        return examenMedicoService.findAll();
    }

    /**
     * Obtiene todos los exámenes medicos paginados
     * @param page Página de la lista de exámenes medicos
     * @return Lista de exámenes medicos paginados
     */
    @GetMapping("/examenes/page/{page}")
    @ResponseStatus(HttpStatus.OK)
    public Page<ExamenMedico> listPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 50);
        return examenMedicoService.findAll(pageable);
    }

    /**
     * Busca un examen médico por su ID
     * @param id ID del examen médico a buscar
     * @return Respuesta HTTP con el examen médico encontrado
     */
    @GetMapping("/examenes/id/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<ExamenMedico> examenMedicoOptional = Optional.empty();

        try {
            examenMedicoOptional = examenMedicoService.findById(id);
        } catch (DataAccessException e) {
            response.put("Message", "Error al lanzar la consulta en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (examenMedicoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(examenMedicoOptional.get());
        } else {
            response.put("Message", "El Examen Medico ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Crea un nuevo examen médico
     * @param examenMedico Objeto del examen médico a crear
     * @param result Resultados de la validación de los datos del examen médico
     * @return Respuesta HTTP con el resultado de la creación del examen médico
     */
    @PostMapping("/examenes")
    public ResponseEntity<?> create(@Valid @RequestBody ExamenMedico examenMedico, BindingResult result) {
        ExamenMedico newExamenMedico = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            newExamenMedico = examenMedicoService.saveExamen(examenMedico);
        } catch (DataAccessException e) {
            response.put("Message", "Error al realizar el insert en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("Message", "El examen médico ha sido creado con éxito!!");
        response.put("ExamenMedico", newExamenMedico);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    /**
     * Actualiza un examen médico existente
     * @param examenMedico Objeto del examen médico a actualizar
     * @param result Resultados de la validación de los datos del examen médico
     * @param id ID del examen médico a actualizar
     * @return Respuesta HTTP con el resultado de la actualización del examen médico
     */
    @PutMapping("/examenes/id/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody ExamenMedico examenMedico, BindingResult result, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        Optional<ExamenMedico> examenMedicoOptional = Optional.empty();
        try {
            examenMedicoOptional = examenMedicoService.findById(id);
        } catch (DataAccessException e) {
            response.put("Message", "Error al lanzar la consulta en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (examenMedicoOptional.isEmpty()) {
            response.put("Message", "Error: no se pudo editar, el examen medico ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        ExamenMedico currentExamenMedico = examenMedicoOptional.get();

        try {
            currentExamenMedico.setTipoExamen(examenMedico.getTipoExamen());
            currentExamenMedico.setFechaExamen(examenMedico.getFechaExamen());
            currentExamenMedico.setHoraExamen(examenMedico.getHoraExamen());
            currentExamenMedico.setLugarExamen(examenMedico.getLugarExamen());
            currentExamenMedico.setObservacionExamen(examenMedico.getObservacionExamen());
            currentExamenMedico.setEstadoExamen(examenMedico.getEstadoExamen());
            currentExamenMedico.setPacientesIdpaciente(examenMedico.getPacientesIdpaciente());
            currentExamenMedico.setMedicosIdmedico(examenMedico.getMedicosIdmedico());
            // Actualizar otros campos aquí según sea necesario

            ExamenMedico updatedExamenMedico = examenMedicoService.updateExamen(currentExamenMedico);

            response.put("Message", "El examen medico ha sido actualizado con éxito!!");
            response.put("Examen Medico", updatedExamenMedico);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (DataAccessException e) {
            response.put("Message", "Error al actualizar el examen medico en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Elimina un examen médico por su ID
     * @param id ID del examen médico a eliminar
     * @return Respuesta HTTP con el resultado de la eliminación del examen médico
     */
    @DeleteMapping("/examenes/id/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<ExamenMedico> examenMedicoOptional = examenMedicoService.findById(id);

        if (examenMedicoOptional.isPresent()) {
            try {
                examenMedicoService.deleteById(id);
                response.put("Message", "El examen medico ha sido eliminado con éxito!!");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
            } catch (DataAccessException e) {
                response.put("Message", "Error al eliminar el examen medico en la Base de Datos");
                response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            response.put("Message", "Error: El examen medico con ID ".concat(id.toString()).concat(" no existe en la base de datos!"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

}