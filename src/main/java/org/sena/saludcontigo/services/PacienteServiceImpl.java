package org.sena.saludcontigo.services;

import org.sena.saludcontigo.repositories.IPacienteRepository;
import org.sena.saludcontigo.repositories.IRoleRepository;
import org.sena.saludcontigo.entities.Paciente;
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
public class PacienteServiceImpl implements IPacienteService {

    @Autowired
    private IPacienteRepository pacienteRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<Paciente> findAll() {
        return pacienteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Paciente> findAll(Pageable pageable) {
        return this.pacienteRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> findById(@NonNull Long id) {
        return pacienteRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Paciente findByDocument(String document){
        return pacienteRepository.findByDocument(document);
    }

    @Override
    @Transactional
    public Paciente save(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    @Override
    @Transactional
    public Paciente savePacienteRol(Paciente paciente) {

        // Obtén el rol de paciente
        Role role = roleRepository.findByRolename("ROLE_PATIENT")
                .orElseThrow(() -> new RuntimeException("ROLE_PATIENT not found"));

        // Asigna el rol al paciente
        paciente.setRole(role);

        paciente.setUsername(paciente.getNumeroDocumento());
        paciente.setPassword(passwordEncoder.encode(paciente.getTipoDocumento() + paciente.getNumeroDocumento()));
        paciente.setActive(1);  // Estado activo
        paciente.setCreatedAt(new Date());
        paciente.setAdmin(false);

        // Guarda el paciente
        return pacienteRepository.save(paciente);
    }

    @Override
    @Transactional
    public Paciente updatePacienteRol(Paciente paciente) {

        // Asigna la fecha de modificación
        paciente.setUpdated_At(new Date());

        // Actualiza el paciente
        return pacienteRepository.save(paciente);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        pacienteRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByDocument(String document) {
        pacienteRepository.deleteByDocument(document);
    }

}
