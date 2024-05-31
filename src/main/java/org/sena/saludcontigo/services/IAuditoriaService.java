package org.sena.saludcontigo.services;

import org.sena.saludcontigo.entities.Auditoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAuditoriaService {

    public List<Auditoria> findAll();

    public Page<Auditoria> findAll(Pageable pageable);

}
