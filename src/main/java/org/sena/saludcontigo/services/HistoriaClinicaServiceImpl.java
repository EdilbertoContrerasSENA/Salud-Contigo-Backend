package org.sena.saludcontigo.services;

import org.sena.saludcontigo.repositories.IHistoriaClinicaRepository;
import org.sena.saludcontigo.entities.HistoriaClinica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HistoriaClinicaServiceImpl implements IHistoriaClinicaService {

    @Autowired
    private IHistoriaClinicaRepository historiaClinicaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<HistoriaClinica> findAll() {
        return historiaClinicaRepository.findAll();
    }

    @Override
    public Page<HistoriaClinica> findAll(Pageable pageable) {
        return this.historiaClinicaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HistoriaClinica> findById(@NonNull Long id) {
        return historiaClinicaRepository.findById(id);
    }

    @Override
    @Transactional
    public HistoriaClinica saveHistoria(HistoriaClinica historiaClinica) {
        return historiaClinicaRepository.save(historiaClinica);
    }

    @Override
    @Transactional
    public HistoriaClinica updateHistoria(HistoriaClinica historiaClinica) {
        return historiaClinicaRepository.save(historiaClinica);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        historiaClinicaRepository.deleteById(id);
    }

}
