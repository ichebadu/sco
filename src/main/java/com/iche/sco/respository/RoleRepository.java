package com.iche.sco.respository;

import com.iche.sco.enums.Permissions;
import com.iche.sco.model.Role;
import com.iche.sco.model.SystemPermissions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;


public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String roleName);

    boolean existsByRoleName(String roleName);
}
