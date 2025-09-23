package com.project.backend.dto;

public record LoginRequestDto(
    String region,
    String site,
    String name,
    String phone
) {}
