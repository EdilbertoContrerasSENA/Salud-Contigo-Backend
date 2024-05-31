package org.sena.saludcontigo.services;

import org.sena.saludcontigo.entities.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface IPacienteService {

    public List<Paciente> findAll();

    public Page<Paciente> findAll(Pageable pageable);

//    public Paciente findById(Long id);

    public Optional<Paciente> findById(@NonNull Long id);

    public Paciente findByDocument(String document);

    public Paciente save(Paciente paciente);

    public Paciente savePacienteRol(Paciente paciente);

    public Paciente updatePacienteRol(Paciente paciente);

    public void deleteById(Long id);

    public void deleteByDocument(String document);
}
