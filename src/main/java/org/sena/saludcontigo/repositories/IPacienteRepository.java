package org.sena.saludcontigo.repositories;

import org.sena.saludcontigo.entities.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface IPacienteRepository extends JpaRepository<Paciente, Long> {
    @Query("select p from Paciente p where p.numeroDocumento = ?1")
    Paciente findByDocument(String document);

    @Modifying
    @Query("DELETE FROM Paciente p WHERE p.numeroDocumento = ?1")
    void deleteByDocument(String document);

    Page<Paciente> findAll(Pageable pageable);

    Optional<Paciente> findByUsername(String name);
}
