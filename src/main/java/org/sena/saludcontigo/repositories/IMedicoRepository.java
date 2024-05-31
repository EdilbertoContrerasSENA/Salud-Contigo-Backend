package org.sena.saludcontigo.repositories;

import org.sena.saludcontigo.entities.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface IMedicoRepository extends JpaRepository<Medico, Long> {
    @Query("select m from Medico m where m.numeroDocumento = ?1")
    Medico findByDocument(String document);

    @Modifying
    @Query("DELETE FROM Medico m WHERE m.numeroDocumento = ?1")
    void deleteByDocument(String document);

    Page<Medico> findAll(Pageable pageable);

    Optional<Medico> findByUsername(String name);
}
