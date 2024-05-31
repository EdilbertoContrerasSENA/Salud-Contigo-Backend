package org.sena.saludcontigo.services;

import org.sena.saludcontigo.entities.Administrador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface IAdministradorService {

    public List<Administrador> findAll();

    public Page<Administrador> findAll(Pageable pageable);

    public Optional<Administrador> findById(@NonNull Long id);

    public Administrador findByDocument(String document);

    public Administrador save(Administrador administrador);

    public Administrador saveAdministradorRol(Administrador administrador);

    public Administrador updateAdministradorRol(Administrador administrador);

    public void deleteById(Long id);

    public void deleteByDocument(String document);
}
