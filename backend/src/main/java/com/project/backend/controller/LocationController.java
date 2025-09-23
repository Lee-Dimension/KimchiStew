package com.project.backend.controller;

import com.project.backend.dto.RegionDto;
import com.project.backend.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.project.backend.domain.Region;
import com.project.backend.domain.Site;
import com.project.backend.dto.CreateRegionRequestDto;
import com.project.backend.dto.CreateSiteRequestDto;
import com.project.backend.dto.SiteDto;
import com.project.backend.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/locations")
    public ResponseEntity<List<RegionDto>> getAllLocations() {
        List<RegionDto> locations = locationService.findAllRegionsWithSites();
        return ResponseEntity.ok(locations);
    }

    @PostMapping("/locations")
    public ResponseEntity<RegionDto> createRegion(@RequestBody CreateRegionRequestDto requestDto) {
        Region newRegion = locationService.createRegion(requestDto);
        RegionDto responseDto = new RegionDto(newRegion.getId(), newRegion.getName(), Collections.emptyList());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PostMapping("/locations/{regionId}/sites")
    public ResponseEntity<SiteDto> createSite(@PathVariable Integer regionId, @RequestBody CreateSiteRequestDto requestDto) {
        Site newSite = locationService.createSite(regionId, requestDto);
        SiteDto responseDto = new SiteDto(newSite.getId(), newSite.getName());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/region/{regionId}")
    public ResponseEntity<Void> deleteRegion(@PathVariable Integer regionId) {
        locationService.deleteRegion(regionId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/site/{siteId}")
    public ResponseEntity<Void> deleteSite(@PathVariable Integer siteId) {
        locationService.deleteSite(siteId);
        return ResponseEntity.noContent().build();
    }
}
