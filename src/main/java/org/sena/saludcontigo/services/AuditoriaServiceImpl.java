package org.sena.saludcontigo.services;

import org.sena.saludcontigo.repositories.IAuditoriaRepository;
import org.sena.saludcontigo.entities.Auditoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuditoriaServiceImpl implements IAuditoriaService {

    @Autowired
    private IAuditoriaRepository auditoriaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Auditoria> findAll() {
        return (List) this.auditoriaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Auditoria> findAll(Pageable pageable) {
        return this.auditoriaRepository.findAll(pageable);
    }
}
