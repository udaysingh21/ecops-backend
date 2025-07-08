package com.ecops.ecops_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecops.ecops_backend.dto.CriminalRequestDto;
import com.ecops.ecops_backend.dto.CriminalResponseDto;
import com.ecops.ecops_backend.entity.Area;
import com.ecops.ecops_backend.entity.Criminal;
import com.ecops.ecops_backend.entity.PoliceStation;
import com.ecops.ecops_backend.repository.AreaRepository;
import com.ecops.ecops_backend.repository.CriminalRepository;
import com.ecops.ecops_backend.repository.PoliceStationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CriminalServiceImpl implements CriminalService {

    private final CriminalRepository criminalRepository;
    private final AreaRepository areaRepository;
    private final PoliceStationRepository policeStationRepository;

    @Override
    public CriminalResponseDto createCriminal(CriminalRequestDto dto) {
        Area area = areaRepository.findById(dto.getAreaId())
            .orElseThrow(() -> new RuntimeException("Area not found"));

        PoliceStation ps = policeStationRepository.findById(dto.getPoliceStationId())
            .orElseThrow(() -> new RuntimeException("Police Station not found"));

        Criminal criminal = Criminal.builder()
            .name(dto.getName())
            .age(dto.getAge())
            .crimeType(dto.getCrimeType())
            .area(area)
            .policeStation(ps)
            .build();

        Criminal saved = criminalRepository.save(criminal);

        return toDto(saved);
    }

    @Override
    public List<CriminalResponseDto> getAllCriminals() {
        return criminalRepository.findAll()
            .stream()
            .map(this::toDto)
            .toList();
    }

    private CriminalResponseDto toDto(Criminal c) {
        return CriminalResponseDto.builder()
            .id(c.getId())
            .name(c.getName())
            .age(c.getAge())
            .crimeType(c.getCrimeType())
            .area(c.getArea().getName())
            .policeStation(c.getPoliceStation().getName())
            .build();
    }
}
