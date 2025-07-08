package com.ecops.ecops_backend.service;

import java.util.List;

import com.ecops.ecops_backend.dto.CriminalRequestDto;
import com.ecops.ecops_backend.dto.CriminalResponseDto;

public interface CriminalService {
    CriminalResponseDto createCriminal(CriminalRequestDto dto);
    List<CriminalResponseDto> getAllCriminals();
}
