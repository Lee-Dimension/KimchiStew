package com.project.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public record UpdateTestRequestDto(
    @JsonProperty("test_status")
    String testStatus,
    @JsonProperty("pass_date")
    LocalDate passDate
) {}
