package org.sena.saludcontigo.controllers;

import org.sena.saludcontigo.entities.Auditoria;
import org.sena.saludcontigo.services.IAuditoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "*"})
@RestController
@RequestMapping("/api")
public class AuditoriaRestController {

    @Autowired
    private IAuditoriaService auditoriaService;

    /**
     * Obtiene todas las auditorias
     * @return Lista de auditorias
     */
    @GetMapping("/auditorias")
    @ResponseStatus(HttpStatus.OK)
    public List<Auditoria> index() {
        return auditoriaService.findAll();
    }

    /**
     * Obtiene todas las auditorias paginadas
     * @param page Página de la lista de auditorias
     * @return Lista de auditorias paginadas
     */
    @GetMapping("/auditorias/page/{page}")
    @ResponseStatus(HttpStatus.OK)
    public Page<Auditoria> listPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 50);
        return auditoriaService.findAll(pageable);
    }
}
