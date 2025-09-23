package com.project.backend.dto;

import java.util.List;

public record RegionDto(
    Integer id,
    String name,
    List<SiteDto> sites
) {}
