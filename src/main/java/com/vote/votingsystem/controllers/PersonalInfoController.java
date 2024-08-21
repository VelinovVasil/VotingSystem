package com.vote.votingsystem.controllers;

import com.vote.votingsystem.services.PersonalInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/personal-info")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class PersonalInfoController {

    private final PersonalInfoService personalInfoService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPersonalInfo(@RequestParam("file") MultipartFile file) {
            this.personalInfoService.seedPersonalInfo(file);
            return new ResponseEntity<>("File uploaded and database seeded successfully", HttpStatus.OK);
    }

}

