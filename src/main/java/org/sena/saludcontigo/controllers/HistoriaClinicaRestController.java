package org.sena.saludcontigo.controllers;

import org.sena.saludcontigo.entities.HistoriaClinica;
import org.sena.saludcontigo.services.IHistoriaClinicaService;
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
public class HistoriaClinicaRestController {

    @Autowired
    private IHistoriaClinicaService historiaClinicaService;

    /**
     * Obtiene todas las historias clínicas
     * @return Lista de historias clínicas
     */
    @GetMapping("/historias")
    @ResponseStatus(HttpStatus.OK)
    public List<HistoriaClinica> index() {
        return historiaClinicaService.findAll();
    }

    /**
     * Obtiene todas las historias clínicas paginadas
     * @param page Página de la lista de historias clínicas
     * @return Lista de historias clínicas paginadas
     */
    @GetMapping("/historias/page/{page}")
    @ResponseStatus(HttpStatus.OK)
    public Page<HistoriaClinica> listPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 50);
        return historiaClinicaService.findAll(pageable);
    }

    /**
     * Busca una historia clínica por su ID
     * @param id ID de la historia clínica a buscar
     * @return Respuesta HTTP con la historia clínica encontrada
     */
    @GetMapping("/historias/id/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<HistoriaClinica> historiaClinicaOptional = Optional.empty();

        try {
            historiaClinicaOptional = historiaClinicaService.findById(id);
        } catch (DataAccessException e) {
            response.put("Message", "Error al lanzar la consulta en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (historiaClinicaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(historiaClinicaOptional.get());
        } else {
            response.put("Message", "La Historia Clinica ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Crea una nueva historia clínica
     * @param historiaClinica Objeto de la historia clínica a crear
     * @param result Resultados de la validación de los datos de la historia clínica
     * @return Respuesta HTTP con el resultado de la creación de la historia clínica
     */
    @PostMapping("/historias")
    public ResponseEntity<?> create(@Valid @RequestBody HistoriaClinica historiaClinica, BindingResult result) {
        HistoriaClinica newHistoriaClinica = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            newHistoriaClinica = historiaClinicaService.saveHistoria(historiaClinica);
        } catch (DataAccessException e) {
            response.put("Message", "Error al realizar el insert en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("Message", "La historia clínica ha sido creada con éxito!!");
        response.put("HistoriaClinica", newHistoriaClinica);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    /**
     * Actualiza una historia clínica existente
     * @param historiaClinica Objeto de la historia clínica a actualizar
     * @param result Resultados de la validación de los datos de la historia clínica
     * @param id ID de la historia clínica a actualizar
     * @return Respuesta HTTP con el resultado de la actualización de la historia clínica
     */
    @PutMapping("/historias/id/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody HistoriaClinica historiaClinica, BindingResult result, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        Optional<HistoriaClinica> historiaClinicaOptional = Optional.empty();
        try {
            historiaClinicaOptional = historiaClinicaService.findById(id);
        } catch (DataAccessException e) {
            response.put("Message", "Error al lanzar la consulta en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (historiaClinicaOptional.isEmpty()) {
            response.put("Message", "Error: no se pudo editar, la historia clinica ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        HistoriaClinica currentHistoriaClinica = historiaClinicaOptional.get();

        try {
            currentHistoriaClinica.setFechaHistoria(historiaClinica.getFechaHistoria());
            currentHistoriaClinica.setAntecedentesHistoria(historiaClinica.getAntecedentesHistoria());
            currentHistoriaClinica.setExamenesHistoria(historiaClinica.getExamenesHistoria());
            currentHistoriaClinica.setDiagnosticoHistoria(historiaClinica.getDiagnosticoHistoria());
            currentHistoriaClinica.setTratamientoHistoria(historiaClinica.getTratamientoHistoria());
            currentHistoriaClinica.setPacientesIdpaciente(historiaClinica.getPacientesIdpaciente());
            currentHistoriaClinica.setMedicosIdmedico(historiaClinica.getMedicosIdmedico());
            // Actualizar otros campos aquí según sea necesario

            HistoriaClinica updatedHistoriaClinica = historiaClinicaService.updateHistoria(currentHistoriaClinica);

            response.put("Message", "La historia clinica ha sido actualizada con éxito!!");
            response.put("Historia Clinica", updatedHistoriaClinica);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (DataAccessException e) {
            response.put("Message", "Error al actualizar la historia clinica en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Elimina una historia clínica por su ID
     * @param id ID de la historia clínica a eliminar
     * @return Respuesta HTTP con el resultado de la eliminación de la historia clínica
     */
    @DeleteMapping("/historias/id/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<HistoriaClinica> historiaClinicaOptional = historiaClinicaService.findById(id);

        if (historiaClinicaOptional.isPresent()) {
            try {
                historiaClinicaService.deleteById(id);
                response.put("Message", "La historia clinica ha sido eliminada con éxito!!");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
            } catch (DataAccessException e) {
                response.put("Message", "Error al eliminar la historia clinica en la Base de Datos");
                response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            response.put("Message", "Error: La historia clinica con ID ".concat(id.toString()).concat(" no existe en la base de datos!"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

}