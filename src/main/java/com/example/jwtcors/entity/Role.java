package com.example.jwtcors.entity;

import java.util.Set;

public enum Role {
    ADMIN(Set.of(Permission.READ_USERS, Permission.CREATE_USERS,
            Permission.DELETE_USERS)),
    USER(Set.of(Permission.READ_USERS));

    public final Set<Permission> permissions;

    Role(Set<Permission> permissions){
        this.permissions=permissions;
    }


}
