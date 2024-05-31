package org.sena.saludcontigo.services;

import org.sena.saludcontigo.entities.ExamenMedico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface IExamenMedicoService {

    public List<ExamenMedico> findAll();

    public Page<ExamenMedico> findAll(Pageable pageable);

    public Optional<ExamenMedico> findById(@NonNull Long id);

    public ExamenMedico saveExamen(ExamenMedico examenMedico);

    public ExamenMedico updateExamen(ExamenMedico examenMedico);

    public void deleteById(Long id);
}
