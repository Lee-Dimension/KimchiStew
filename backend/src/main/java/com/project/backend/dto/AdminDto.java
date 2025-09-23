package com.project.backend.dto;

import com.project.backend.domain.Admin;

public record AdminDto(
    String id
) {
    public static AdminDto fromEntity(Admin admin) {
        return new AdminDto(admin.getId());
    }
}
