package org.sena.saludcontigo.controllers;

import org.sena.saludcontigo.entities.Administrador;
import org.sena.saludcontigo.services.IAdministradorService;
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
public class AdministradorRestController {

    @Autowired
    private IAdministradorService administradorService;

    /**
     * Obtiene todos los administradores
     * @return Lista de administradores
     */
    @GetMapping("/administradores")
    @ResponseStatus(HttpStatus.OK)
    public List<Administrador> index() {
        return administradorService.findAll();
    }

    /**
     * Obtiene todos los administradores paginados
     * @param page Página de la lista de administradores
     * @return Lista de administradores paginados
     */
    @GetMapping("/administradores/page/{page}")
    @ResponseStatus(HttpStatus.OK)
    public Page<Administrador> listPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return administradorService.findAll(pageable);
    }

    /**
     * Busca un administrador por su ID
     * @param id ID del administrador a buscar
     * @return Respuesta HTTP con el administrador encontrado
     */
    @GetMapping("/administradores/id/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<Administrador> administradorOptional = Optional.empty();

        try {
            administradorOptional = administradorService.findById(id);
        } catch (DataAccessException e) {
            response.put("Message", "Error al lanzar la consulta en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (administradorOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(administradorOptional.get());
        } else {
            response.put("Message", "El administrador ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Crea un nuevo administrador
     * @param administrador Objeto del administrador a crear
     * @param result Resultados de la validación de los datos del administrador
     * @return Respuesta HTTP con el resultado de la creación del administrador
     */
    @PostMapping("/administradores")
    public ResponseEntity<?> create(@Valid @RequestBody Administrador administrador, BindingResult result) {
        Administrador newAdministrador = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            newAdministrador = administradorService.save(administrador);
        } catch (DataAccessException e) {
            response.put("Message", "Error al realizar el insert en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("Message", "El administrador ha sido creado con éxito!!");
        response.put("Administrador", newAdministrador);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Actualiza un administrador existente
     * @param administrador Objeto del administrador con los datos actualizados
     * @param result Resultados de la validación de los datos del administrador
     * @param id ID del administrador a actualizar
     * @return Respuesta HTTP con el resultado de la actualización del administrador
     */
    @PutMapping("/administradores/id/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Administrador administrador, BindingResult result, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        Optional<Administrador> administradorOptional = Optional.empty();
        try {
            administradorOptional = administradorService.findById(id);
        } catch (DataAccessException e) {
            response.put("Message", "Error al lanzar la consulta en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (administradorOptional.isEmpty()) {
            response.put("Message", "Error: no se pudo editar, el administrador ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Administrador currentAdministrador = administradorOptional.get();

        try {
            currentAdministrador.setNombres(administrador.getNombres());
            currentAdministrador.setApellidos(administrador.getApellidos());
            currentAdministrador.setTipoDocumento(administrador.getTipoDocumento());
            currentAdministrador.setNumeroDocumento(administrador.getNumeroDocumento());
            currentAdministrador.setFechaNacimiento(administrador.getFechaNacimiento());
            currentAdministrador.setSexo(administrador.getSexo());
            currentAdministrador.setTelefono(administrador.getTelefono());
            currentAdministrador.setCorreo(administrador.getCorreo());
            currentAdministrador.setDireccion(administrador.getDireccion());
            currentAdministrador.setOcupacion(administrador.getOcupacion());
            // Actualizar otros campos según sea necesario

            Administrador updatedAdministrador = administradorService.updateAdministradorRol(currentAdministrador);

            response.put("Message", "El administrador ha sido actualizado con éxito!!");
            response.put("Administrador", updatedAdministrador);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (DataAccessException e) {
            response.put("Message", "Error al actualizar el administrador en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Elimina un administrador por su ID
     * @param id ID del administrador a eliminar
     * @return Respuesta HTTP con el resultado de la eliminación del administrador
     */
    @DeleteMapping("/administradores/id/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<Administrador> administradorOptional = administradorService.findById(id);

        if (administradorOptional.isPresent()) {
            try {
                administradorService.deleteById(id);
                response.put("Message", "El administrador ha sido eliminado con éxito!!");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
            } catch (DataAccessException e) {
                response.put("Message", "Error al eliminar el administrador en la Base de Datos");
                response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            response.put("Message", "Error: El administrador con ID ".concat(id.toString()).concat(" no existe en la base de datos!"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }


    /*
     * METODOS PERSONALIZADOS
     */

    /**
     * Busca un administrador por su Documento
     * @param doc DOC del administrador a buscar
     * @return Respuesta HTTP con el administrador encontrado
     */
    @GetMapping("/administradores/doc/{doc}")
    public ResponseEntity<?> showByDocument(@PathVariable String doc) {
        Administrador administrador = null;
        Map<String, Object> response = new HashMap<>();

        try {
            administrador = administradorService.findByDocument(doc);
        } catch (DataAccessException e) {
            response.put("Message", "Error al lanzar la consulta en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (administrador == null) {
            response.put("Message", "El administrador Documento: ".concat(doc.concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Administrador>(administrador, HttpStatus.OK);
    }

    /**
     * Actualiza un administrador existente utilizando su número de documento.
     * @param administrador Objeto del administrador con los datos actualizados.
     * @param result Resultados de la validación de los datos del administrador.
     * @param doc Número de documento del administrador a actualizar.
     * @return Respuesta HTTP con el resultado de la actualización del administrador.
     */
    @PutMapping("/administradores/doc/{doc}")
    public ResponseEntity<?> updateByDocument(@Valid @RequestBody Administrador administrador, BindingResult result, @PathVariable String doc) {
        Administrador currentAdministrador = administradorService.findByDocument(doc);
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        if (currentAdministrador == null) {
            response.put("Message", "Error: no se pudo editar, el administrador con documento: " + doc + " no existe en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // Actualizar campos
        currentAdministrador.setNombres(administrador.getNombres());
        currentAdministrador.setApellidos(administrador.getApellidos());
        currentAdministrador.setTipoDocumento(administrador.getTipoDocumento());
        currentAdministrador.setNumeroDocumento(administrador.getNumeroDocumento());
        currentAdministrador.setFechaNacimiento(administrador.getFechaNacimiento());
        currentAdministrador.setSexo(administrador.getSexo());
        currentAdministrador.setTelefono(administrador.getTelefono());
        currentAdministrador.setCorreo(administrador.getCorreo());
        currentAdministrador.setDireccion(administrador.getDireccion());
        currentAdministrador.setOcupacion(administrador.getOcupacion());
        // Actualizar otros campos según sea necesario

        try {
            administradorService.save(currentAdministrador);
        } catch (DataAccessException e) {
            response.put("Message", "Error al actualizar el administrador en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("Message", "El administrador ha sido actualizado con éxito!!");
        response.put("Administrador", currentAdministrador);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Elimina un administrador por su número de documento.
     * @param doc Número de documento del administrador a eliminar.
     * @return Respuesta HTTP con el resultado de la eliminación del administrador.
     */
    @DeleteMapping("/administradores/doc/{doc}")
    public ResponseEntity<?> deleteByDocument(@PathVariable String doc) {
        Map<String, Object> response = new HashMap<>();
        try {
            administradorService.deleteByDocument(doc);
        } catch (DataAccessException e) {
            response.put("Message", "Error al eliminar el administrador en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("Message", "El administrador ha sido eliminado con éxito!!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}