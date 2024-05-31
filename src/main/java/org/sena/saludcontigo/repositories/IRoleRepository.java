package org.sena.saludcontigo.repositories;

import org.sena.saludcontigo.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRolename(String rolename);
}
