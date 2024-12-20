package com.personal.project.service;

import com.personal.model.dto.RoleResponse;
import com.personal.project.adapter.RoleAdapter;
import com.personal.project.model.Role;
import com.personal.project.repository.RoleRepository;
import com.personal.project.repository.UserRepository;
import com.personal.project.service.contract.RolesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
@AllArgsConstructor
@Slf4j
public class RolesServiceImpl implements RolesService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final RoleAdapter roleAdapter;

    @Override
    public List<String> findAllRoles() {
        List<String> allRoles = roleRepository.findAllRoles();
        log.info("Retrieve all distinct roles");
        return allRoles;
    }

    @Override
    public RoleResponse create(String role) {
        Role createdRole = roleRepository.save(Role.builder().name(role).build());
        log.info(format("Create role: %s", createdRole.getName()));
        return roleAdapter.fromRoleToRoleResponse(createdRole);
    }

    @Override
    public boolean rename(String oldRole, String newRole) {
        int isRenamed = roleRepository.renameByName(oldRole, newRole);
        if (isRenamed <= 0) {
            log.warn(format("Can't rename %s", oldRole));
            return false;
        }
        log.info(format("Role renamed: %s", newRole));
        return true;
    }

    @Override
    public boolean delete(String role) {
        int isDeleted = roleRepository.deleteByName(role);
        if (isDeleted <= 0) {
            log.warn(format("Can't delete %s", role));
            return false;
        }
        log.info(format("Role deleted: %s", role));
        return true;
    }

    @Override
    public List<RoleResponse> findAllRolesUsersCount() {
        List<Role> roles = roleRepository.findAll();
        List<RoleResponse> roleResponses = roles.stream().map(roleAdapter::fromRoleToRoleResponse).toList();
        roleResponses.forEach(role -> role.setUserCount(userRepository.findAllUsersCountByRole(role.getName())));
        log.info("Retrieve all roles' user count");
        return roleResponses;
    }
}
