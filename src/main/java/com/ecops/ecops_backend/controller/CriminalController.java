package com.ecops.ecops_backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecops.ecops_backend.dto.CriminalRequestDto;
import com.ecops.ecops_backend.dto.CriminalResponseDto;
import com.ecops.ecops_backend.service.CriminalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/criminals")
@RequiredArgsConstructor
public class CriminalController {

    private final CriminalService criminalService;

    @PostMapping("/create")
    public ResponseEntity<CriminalResponseDto> create(@RequestBody CriminalRequestDto request) {
        CriminalResponseDto created = criminalService.createCriminal(request);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CriminalResponseDto>> getAll() {
        return ResponseEntity.ok(criminalService.getAllCriminals());
    }
}
