package com.iche.sco.respository;

import com.iche.sco.enums.Permissions;
import com.iche.sco.model.SystemPermissions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface SystemPermissionRepository extends JpaRepository<SystemPermissions, Long> {
    boolean existsByPermissions(Permissions permission);
    Set<SystemPermissions> findByPermissionsIn(Set<Permissions> permissions);
}
