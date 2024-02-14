package com.iche.sco.seeder;

import com.iche.sco.enums.Permissions;
import com.iche.sco.model.Role;
import com.iche.sco.model.SystemPermissions;
import com.iche.sco.respository.RoleRepository;
import com.iche.sco.respository.SystemPermissionRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;


@RequiredArgsConstructor
@Component
public class RoleSeeder implements CommandLineRunner {
    private final SystemPermissionRepository systemPermissionRepository;
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        Permissions[] permissions = Permissions.values();

        for (Permissions permission : permissions) {
            if (!systemPermissionRepository.existsByPermissions(permission)) {
                systemPermissionRepository.save(SystemPermissions.builder()
                        .name(permission.getName())
                        .description(permission.getDescription())
                        .permissions(permission)
                        .build());
            }
        }
        roleInitializer("ROLE_USER",Set.of(
                Permissions.USER_SUBSCRIBE,
                Permissions.USER_PAYMENT,
                Permissions.USER_BUY
        ));
        roleInitializer("ROLE_MERCHANT",Set.of(
                Permissions.MERCHANT_HIGHER_PAYMENT,
                Permissions.MERCHANT_LOAN,
                Permissions.MERCHANT_HIGHER_RECEIVE
        ));
        roleInitializer("ROLE_SUPER_ADMIN",Set.of(
                Permissions.SUPER_ADMIN_BLOCK_USER,
                Permissions.SUPER_ADMIN_UNBLOCK_USER,
                Permissions.SUPER_ADMIN_DELETE_USER,
                Permissions.SUPER_ADMIN_DEACTIVATE_USER
        ));
    }
        private void roleInitializer (String roleName, Set<Permissions> permissions) {
            if (!roleRepository.existsByRoleName(roleName)) {
                Set<SystemPermissions> systemPermission = systemPermissionRepository.findByPermissionsIn(permissions);
                Role role = Role.builder()
                        .roleName(roleName)
                        .systemPermissions(systemPermission)
                        .build();
                roleRepository.save(role);
            }
        }
}
