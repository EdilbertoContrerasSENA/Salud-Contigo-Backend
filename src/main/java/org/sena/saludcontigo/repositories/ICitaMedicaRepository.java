package org.sena.saludcontigo.repositories;

import org.sena.saludcontigo.entities.CitaMedica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICitaMedicaRepository extends JpaRepository<CitaMedica, Long> {
    Page<CitaMedica> findAll(Pageable pageable);
}
