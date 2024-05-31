package org.sena.saludcontigo.repositories;

import org.sena.saludcontigo.entities.ExamenMedico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IExamenMedicoRepository extends JpaRepository<ExamenMedico, Long> {
    Page<ExamenMedico> findAll(Pageable pageable);
}
