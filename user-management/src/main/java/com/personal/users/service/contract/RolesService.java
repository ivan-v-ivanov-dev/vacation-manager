package com.personal.users.service.contract;

import com.personal.users.model.Role;

import java.util.List;

public interface RolesService {
    List<String> findAllRoles();

    Role create(String role);

    boolean rename(String oldRole, String newRole);

    boolean delete(String role);

    List<Role> findAllRolesUsersCount();
}