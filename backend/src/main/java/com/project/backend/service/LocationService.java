package com.project.backend.service;

import com.project.backend.dto.RegionDto;
import com.project.backend.dto.SiteDto;
import com.project.backend.domain.Region;
import com.project.backend.domain.Site;
import com.project.backend.dto.CreateRegionRequestDto;
import com.project.backend.dto.CreateSiteRequestDto;
import com.project.backend.repository.RegionRepository;
import com.project.backend.repository.SiteRepository;
import com.project.backend.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationService {

    private final RegionRepository regionRepository;
    private final SiteRepository siteRepository;
    private final UserRepository userRepository;

    public List<RegionDto> findAllRegionsWithSites() {
        return regionRepository.findAllWithSites().stream()
                .map(region -> new RegionDto(
                        region.getId(),
                        region.getName(),
                        region.getSites().stream()
                                .map(site -> new SiteDto(site.getId(), site.getName()))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    @Transactional
    public Region createRegion(CreateRegionRequestDto requestDto) {
        if (regionRepository.existsByName(requestDto.name())) {
            throw new IllegalArgumentException("Region with name '" + requestDto.name() + "' already exists.");
        }
        Region region = new Region();
        region.setName(requestDto.name());
        return regionRepository.save(region);
    }

    @Transactional
    public Site createSite(Integer regionId, CreateSiteRequestDto requestDto) {
        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new IllegalArgumentException("Region not found with id: " + regionId));

        if (siteRepository.existsByNameAndRegionId(requestDto.name(), regionId)) {
            throw new IllegalArgumentException("Site with name '" + requestDto.name() + "' already exists in this region.");
        }

        Site site = new Site();
        site.setName(requestDto.name());
        site.setRegion(region);
        return siteRepository.save(site);
    }

    @Transactional
    public void deleteRegion(Integer regionId) {
        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new IllegalArgumentException("Region not found with id: " + regionId));

        // Delete all users associated with sites in this region
        region.getSites().forEach(site -> userRepository.deleteBySiteId(site.getId()));

        // Region deletion will cascade to sites due to CascadeType.ALL and orphanRemoval=true
        regionRepository.delete(region);
    }

    @Transactional
    public void deleteSite(Integer siteId) {
        Site site = siteRepository.findById(siteId)
                .orElseThrow(() -> new IllegalArgumentException("Site not found with id: " + siteId));

        // Delete all users associated with this site
        userRepository.deleteBySiteId(siteId);

        siteRepository.delete(site);
    }
}
