package org.sena.saludcontigo.services;

import org.sena.saludcontigo.entities.HistoriaClinica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface IHistoriaClinicaService {

    public List<HistoriaClinica> findAll();

    public Page<HistoriaClinica> findAll(Pageable pageable);

    public Optional<HistoriaClinica> findById(@NonNull Long id);

    public HistoriaClinica saveHistoria(HistoriaClinica historiaClinica);

    public HistoriaClinica updateHistoria(HistoriaClinica historiaClinica);

    public void deleteById(Long id);
}
