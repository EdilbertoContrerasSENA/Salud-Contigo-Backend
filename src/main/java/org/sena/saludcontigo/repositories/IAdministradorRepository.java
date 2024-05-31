package org.sena.saludcontigo.repositories;

import org.sena.saludcontigo.entities.Administrador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface IAdministradorRepository extends JpaRepository<Administrador, Long> {
    @Query("select a from Administrador a where a.numeroDocumento = ?1")
    Administrador findByDocument(String document);

    @Modifying
    @Query("DELETE FROM Administrador a WHERE a.numeroDocumento = ?1")
    void deleteByDocument(String document);

    Page<Administrador> findAll(Pageable pageable);

    Optional<Administrador> findByUsername(String name);
}
