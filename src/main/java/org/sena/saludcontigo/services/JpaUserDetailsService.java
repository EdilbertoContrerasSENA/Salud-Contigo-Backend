package org.sena.saludcontigo.services;

import org.sena.saludcontigo.repositories.IAdministradorRepository;
import org.sena.saludcontigo.repositories.IMedicoRepository;
import org.sena.saludcontigo.repositories.IPacienteRepository;
import org.sena.saludcontigo.entities.Administrador;
import org.sena.saludcontigo.entities.Medico;
import org.sena.saludcontigo.entities.Paciente;
import org.sena.saludcontigo.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private IPacienteRepository pacienteRepository;

    @Autowired
    private IMedicoRepository medicoRepository;

    @Autowired
    private IAdministradorRepository administradorRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = findUserDetails(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException(String.format("Username %s no existe en el sistema", username));
        }
        return userDetails;
    }

    private UserDetails findUserDetails(String username) {
        Optional<Paciente> optionalPaciente = pacienteRepository.findByUsername(username);
        if (optionalPaciente.isPresent()) {
            return buildUserDetails(optionalPaciente.get());
        }

        Optional<Medico> optionalMedico = medicoRepository.findByUsername(username);
        if (optionalMedico.isPresent()) {
            return buildUserDetails(optionalMedico.get());
        }

        Optional<Administrador> optionalAdministrador = administradorRepository.findByUsername(username);
        if (optionalAdministrador.isPresent()) {
            return buildUserDetails(optionalAdministrador.get());
        }

        return null;
    }

    private UserDetails buildUserDetails(Object user) {
        String username = null;
        String password = null;
        Role role = null;

        if (user instanceof Paciente) {
            Paciente paciente = (Paciente) user;
            username = paciente.getUsername();
            password = paciente.getPassword();
            role = paciente.getRole();
        } else if (user instanceof Medico) {
            Medico medico = (Medico) user;
            username = medico.getUsername();
            password = medico.getPassword();
            role = medico.getRole();
        } else if (user instanceof Administrador) {
            Administrador administrador = (Administrador) user;
            username = administrador.getUsername();
            password = administrador.getPassword();
            role = administrador.getRole();
        }

        if (username == null || password == null || role == null) {
            throw new UsernameNotFoundException("User not found or role is missing");
        }

        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role.getRolename()));

        return new org.springframework.security.core.userdetails.User(username,
                password,
                true,
                true,
                true,
                true,
                authorities);
    }
}
