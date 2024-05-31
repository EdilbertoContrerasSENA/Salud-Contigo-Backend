package org.sena.saludcontigo.services;

import org.sena.saludcontigo.repositories.IAdministradorRepository;
import org.sena.saludcontigo.repositories.IRoleRepository;
import org.sena.saludcontigo.entities.Administrador;
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
public class AdministradorServiceImpl implements IAdministradorService {

    @Autowired
    private IAdministradorRepository administradorRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<Administrador> findAll() {
        return administradorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Administrador> findAll(Pageable pageable) {
        return this.administradorRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Administrador> findById(@NonNull Long id) {
        return administradorRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Administrador findByDocument(String document){
        return administradorRepository.findByDocument(document);
    }

    @Override
    @Transactional
    public Administrador save(Administrador administrador) {
        return administradorRepository.save(administrador);
    }

    @Override
    @Transactional
    public Administrador saveAdministradorRol(Administrador administrador) {

        // Obtén el rol de paciente
        Role role = roleRepository.findByRolename("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("ROLE_ADMIN not found"));

        // Asigna el rol al paciente
        administrador.setRole(role);

        administrador.setUsername(administrador.getNumeroDocumento());
        administrador.setPassword(passwordEncoder.encode(administrador.getTipoDocumento() + administrador.getNumeroDocumento()));
        administrador.setActive(1);  // Estado activo
        administrador.setCreatedAt(new Date());
        administrador.setAdmin(true);

        // Guardar el paciente
        return administradorRepository.save(administrador);
    }

    @Override
    @Transactional
    public Administrador updateAdministradorRol(Administrador administrador) {

        // Asigna la fecha de modificación
        administrador.setUpdated_At(new Date());

        // Actualiza el administrador
        return administradorRepository.save(administrador);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        administradorRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByDocument(String document) {
        administradorRepository.deleteByDocument(document);
    }

}
