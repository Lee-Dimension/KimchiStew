package com.project.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public record UpdateEducationRequestDto(
    @JsonProperty("completion_date")
    LocalDate completionDate
) {}
