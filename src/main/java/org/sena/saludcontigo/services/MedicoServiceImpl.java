package org.sena.saludcontigo.services;

import org.sena.saludcontigo.repositories.IMedicoRepository;
import org.sena.saludcontigo.repositories.IRoleRepository;
import org.sena.saludcontigo.entities.Medico;
import org.sena.saludcontigo.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MedicoServiceImpl implements IMedicoService {

    @Autowired
    private IMedicoRepository medicoRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<Medico> findAll() {
        return medicoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Medico> findAll(Pageable pageable) {
        return this.medicoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Medico> findById(@NonNull Long id) {
        return medicoRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Medico findByDocument(String document){
        return medicoRepository.findByDocument(document);
    }

    @Override
    @Transactional
    public Medico save(Medico medico) {
        return medicoRepository.save(medico);
    }

    @Override
    @Transactional
    public Medico saveMedicoRol(Medico medico) {

        // Obtén el rol de paciente
        Role role = roleRepository.findByRolename("ROLE_DOCTOR")
                .orElseThrow(() -> new RuntimeException("ROLE_DOCTOR not found"));

        // Asigna el rol al paciente
        medico.setRole(role);

        medico.setUsername(medico.getNumeroDocumento());
        medico.setPassword(passwordEncoder.encode(medico.getTipoDocumento() + medico.getNumeroDocumento()));
        medico.setActive(1);  // Estado activo
        medico.setCreatedAt(new Date());
        medico.setAdmin(false);

        // Guardar el paciente
        return medicoRepository.save(medico);
    }

    @Override
    @Transactional
    public Medico updateMedicoRol(Medico medico) {

        // Asigna la fecha de modificación
        medico.setUpdated_At(new Date());

        // Actualiza el médico
        return medicoRepository.save(medico);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        medicoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByDocument(String document) {
        medicoRepository.deleteByDocument(document);
    }

}
