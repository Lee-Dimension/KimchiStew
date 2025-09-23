package com.project.backend.dto;

import com.project.backend.domain.User;

public record UserListDto(
    Integer id,
    String name,
    Integer regionId,
    String regionName,
    Integer siteId,
    String siteName
) {
    public static UserListDto fromEntity(User user) {
        Integer siteId = (user.getSite() != null) ? user.getSite().getId() : null;
        String siteName = (user.getSite() != null) ? user.getSite().getName() : null;
        Integer regionId = (user.getSite() != null && user.getSite().getRegion() != null) ? user.getSite().getRegion().getId() : null;
        String regionName = (user.getSite() != null && user.getSite().getRegion() != null) ? user.getSite().getRegion().getName() : null;
        return new UserListDto(user.getId(), user.getName(), regionId, regionName, siteId, siteName);
    }
}