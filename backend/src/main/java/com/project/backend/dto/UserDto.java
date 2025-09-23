package com.project.backend.dto;

public record UserDto(
    Integer id,
    String name,
    String phone,
    String region,
    String site
) {
    // You can add a constructor or a static factory method for easy conversion from the User entity
    public static UserDto fromEntity(com.project.backend.domain.User user) {
        return new UserDto(
            user.getId(),
            user.getName(),
            user.getPhone(),
            user.getSite().getRegion().getName(),
            user.getSite().getName()
        );
    }
}
