package org.sena.saludcontigo.services;

import org.sena.saludcontigo.entities.CitaMedica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface ICitaMedicaService {

    public List<CitaMedica> findAll();

    public Page<CitaMedica> findAll(Pageable pageable);

    public Optional<CitaMedica> findById(@NonNull Long id);

    public CitaMedica saveCita(CitaMedica citaMedica);

    public CitaMedica updateCita(CitaMedica citaMedica);

    public void deleteById(Long id);

}
