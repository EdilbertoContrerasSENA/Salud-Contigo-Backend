package org.sena.saludcontigo.repositories;

import org.sena.saludcontigo.entities.HistoriaClinica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHistoriaClinicaRepository extends JpaRepository<HistoriaClinica, Long> {
    Page<HistoriaClinica> findAll(Pageable pageable);
}
