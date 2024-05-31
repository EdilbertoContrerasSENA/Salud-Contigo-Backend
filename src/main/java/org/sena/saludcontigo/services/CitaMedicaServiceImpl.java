package org.sena.saludcontigo.services;

import org.sena.saludcontigo.repositories.ICitaMedicaRepository;
import org.sena.saludcontigo.entities.CitaMedica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CitaMedicaServiceImpl implements ICitaMedicaService {

    @Autowired
    private ICitaMedicaRepository citaMedicaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CitaMedica> findAll() {
        return citaMedicaRepository.findAll();
    }

    @Override
    public Page<CitaMedica> findAll(Pageable pageable) {
        return this.citaMedicaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CitaMedica> findById(@NonNull Long id) {
        return citaMedicaRepository.findById(id);
    }

    @Override
    @Transactional
    public CitaMedica saveCita(CitaMedica citaMedica) {
        // Asigna el estado de la Cita
        citaMedica.setEstadoCita("Agendada");

        return citaMedicaRepository.save(citaMedica);
    }

    @Override
    @Transactional
    public CitaMedica updateCita(CitaMedica citaMedica) {
        return citaMedicaRepository.save(citaMedica);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        citaMedicaRepository.deleteById(id);
    }

}
