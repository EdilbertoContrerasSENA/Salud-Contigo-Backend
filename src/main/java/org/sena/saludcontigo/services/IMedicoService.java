package org.sena.saludcontigo.services;

import org.sena.saludcontigo.entities.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface IMedicoService {

    public List<Medico> findAll();

    public Page<Medico> findAll(Pageable pageable);

    public Optional<Medico> findById(@NonNull Long id);

    public Medico findByDocument(String document);

    public Medico save(Medico medico);

    public Medico saveMedicoRol(Medico medico);

    public Medico updateMedicoRol(Medico medico);

    public void deleteById(Long id);

    public void deleteByDocument(String document);
}
