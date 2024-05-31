package org.sena.saludcontigo.repositories;

import org.sena.saludcontigo.entities.Auditoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuditoriaRepository extends JpaRepository<Auditoria, Long> {

    Page<Auditoria> findAll(Pageable pageable);
}
