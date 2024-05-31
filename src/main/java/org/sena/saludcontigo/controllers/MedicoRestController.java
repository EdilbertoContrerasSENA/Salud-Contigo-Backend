package org.sena.saludcontigo.controllers;

import org.sena.saludcontigo.entities.Medico;
import org.sena.saludcontigo.services.IMedicoService;
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

@CrossOrigin(origins = {"http://localhost:4200", "*"})
@RestController
@RequestMapping("/api")
public class MedicoRestController {

    @Autowired
    private IMedicoService medicoService;

    /**
     * Obtiene todos los médicos
     * @return Lista de médicos
     */
    @GetMapping("/medicos")
    @ResponseStatus(HttpStatus.OK)
    public List<Medico> index() {
        return medicoService.findAll();
    }

    /**
     * Obtiene todos los medicos paginados
     * @param page Página de la lista de medicos
     * @return Lista de medicos paginados
     */
    @GetMapping("/medicos/page/{page}")
    @ResponseStatus(HttpStatus.OK)
    public Page<Medico> listPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 50);
        return medicoService.findAll(pageable);
    }

    /**
     * Busca un médico por su ID
     * @param id ID del médico a buscar
     * @return Respuesta HTTP con el médico encontrado
     */
    @GetMapping("/medicos/id/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<Medico> medicoOptional = Optional.empty();

        try {
            medicoOptional = medicoService.findById(id);
        } catch (DataAccessException e) {
            response.put("Message", "Error al lanzar la consulta en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (medicoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(medicoOptional.get());
        } else {
            response.put("Message", "El medico ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Crea un nuevo médico
     * @param medico Objeto del médico a crear
     * @param result Resultados de la validación de los datos del médico
     * @return Respuesta HTTP con el resultado de la creación del médico
     */
    @PostMapping("/medicos")
    public ResponseEntity<?> create(@Valid @RequestBody Medico medico, BindingResult result) {
        Medico newMedico = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            newMedico = medicoService.save(medico);
        } catch (DataAccessException e) {
            response.put("Message", "Error al realizar el insert en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("Message", "El médico ha sido creado con éxito!!");
        response.put("Médico", newMedico);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    /**
     * Actualiza un médico existente
     * @param medico Objeto del médico con los datos actualizados
     * @param result Resultados de la validación de los datos del médico
     * @param id ID del médico a actualizar
     * @return Respuesta HTTP con el resultado de la actualización del médico
     */
    @PutMapping("/medicos/id/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Medico medico, BindingResult result, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        Optional<Medico> medicoOptional = Optional.empty();
        try {
            medicoOptional = medicoService.findById(id);
        } catch (DataAccessException e) {
            response.put("Message", "Error al lanzar la consulta en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (medicoOptional.isEmpty()) {
            response.put("Message", "Error: no se pudo editar, el médico ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Medico currentMedico = medicoOptional.get();

        try {
            currentMedico.setNombres(medico.getNombres());
            currentMedico.setApellidos(medico.getApellidos());
            currentMedico.setTipoDocumento(medico.getTipoDocumento());
            currentMedico.setNumeroDocumento(medico.getNumeroDocumento());
            currentMedico.setFechaNacimiento(medico.getFechaNacimiento());
            currentMedico.setSexo(medico.getSexo());
            currentMedico.setTelefono(medico.getTelefono());
            currentMedico.setCorreo(medico.getCorreo());
            currentMedico.setDireccion(medico.getDireccion());
            currentMedico.setEspecialidad(medico.getEspecialidad());
            // Actualizar otros campos según sea necesario

            Medico updatedMedico = medicoService.updateMedicoRol(currentMedico);

            response.put("Message", "El médico ha sido actualizado con éxito!!");
            response.put("Medico", updatedMedico);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (DataAccessException e) {
            response.put("Message", "Error al actualizar el médico en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Elimina un médico por su ID
     * @param id ID del médico a eliminar
     * @return Respuesta HTTP con el resultado de la eliminación del médico
     */
    @DeleteMapping("/medicos/id/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<Medico> medicoOptional = medicoService.findById(id);

        if (medicoOptional.isPresent()) {
            try {
                medicoService.deleteById(id);
                response.put("Message", "El médico ha sido eliminado con éxito!!");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
            } catch (DataAccessException e) {
                response.put("Message", "Error al eliminar el médico en la Base de Datos");
                response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            response.put("Message", "Error: El médico con ID ".concat(id.toString()).concat(" no existe en la base de datos!"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }


    /*
     * METODOS PERSONALIZADOS
     */

    /**
     * Busca un médico por su documento
     * @param doc DOC del médico a buscar
     * @return Respuesta HTTP con el médico encontrado
     */
    @GetMapping("/medicos/doc/{doc}")
    public ResponseEntity<?> show(@PathVariable String doc) {
        Medico medico = null;
        Map<String, Object> response = new HashMap<>();

        try {
            medico = medicoService.findByDocument(doc);
        } catch (DataAccessException e) {
            response.put("Message", "Error al lanzar la consulta en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (medico == null) {
            response.put("Message", "El médico documento: ".concat(doc.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Medico>(medico, HttpStatus.OK);
    }

    /**
     * Actualiza un médico existente utilizando su número de documento.
     * @param medico Objeto del médico con los datos actualizados.
     * @param result Resultados de la validación de los datos del médico.
     * @param doc Número de documento del médico a actualizar.
     * @return Respuesta HTTP con el resultado de la actualización del médico.
     */
    @PutMapping("/medicos/doc/{doc}")
    public ResponseEntity<?> updateByDocument(@Valid @RequestBody Medico medico, BindingResult result, @PathVariable String doc) {
        Medico currentMedico = medicoService.findByDocument(doc);
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        if (currentMedico == null) {
            response.put("Message", "Error: no se pudo editar, el médico con documento: " + doc + " no existe en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // Actualizar campos
        currentMedico.setNombres(medico.getNombres());
        currentMedico.setApellidos(medico.getApellidos());
        currentMedico.setTipoDocumento(medico.getTipoDocumento());
        currentMedico.setNumeroDocumento(medico.getNumeroDocumento());
        currentMedico.setFechaNacimiento(medico.getFechaNacimiento());
        currentMedico.setSexo(medico.getSexo());
        currentMedico.setTelefono(medico.getTelefono());
        currentMedico.setCorreo(medico.getCorreo());
        currentMedico.setDireccion(medico.getDireccion());
        currentMedico.setEspecialidad(medico.getEspecialidad());
        // Actualizar otros campos según sea necesario

        try {
            medicoService.save(currentMedico);
        } catch (DataAccessException e) {
            response.put("Message", "Error al actualizar el médico en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("Message", "El médico ha sido actualizado con éxito!!");
        response.put("Médico", currentMedico);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Elimina un médico por su número de documento.
     * @param doc Número de documento del médico a eliminar.
     * @return Respuesta HTTP con el resultado de la eliminación del médico.
     */
    @DeleteMapping("/medicos/doc/{doc}")
    public ResponseEntity<?> deleteByDocument(@PathVariable String doc) {
        Map<String, Object> response = new HashMap<>();
        try {
            medicoService.deleteByDocument(doc);
        } catch (DataAccessException e) {
            response.put("Message", "Error al eliminar el médico en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("Message", "El médico ha sido eliminado con éxito!!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}