package org.sena.saludcontigo.services;

import org.sena.saludcontigo.repositories.IExamenMedicoRepository;
import org.sena.saludcontigo.entities.ExamenMedico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ExamenMedicoServiceImpl implements IExamenMedicoService {

    @Autowired
    private IExamenMedicoRepository examenMedicoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ExamenMedico> findAll() {
        return examenMedicoRepository.findAll();
    }

    @Override
    public Page<ExamenMedico> findAll(Pageable pageable) {
        return this.examenMedicoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ExamenMedico> findById(@NonNull Long id) {
        return examenMedicoRepository.findById(id);
    }

    @Override
    @Transactional
    public ExamenMedico saveExamen(ExamenMedico examenMedico) {
        // Asigna el estado del examen
        examenMedico.setEstadoExamen("Agendado");

        return examenMedicoRepository.save(examenMedico);
    }

    @Override
    @Transactional
    public ExamenMedico updateExamen(ExamenMedico examenMedico) {
        return examenMedicoRepository.save(examenMedico);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        examenMedicoRepository.deleteById(id);
    }

}
