package com.vote.votingsystem.controllers;

import com.vote.votingsystem.services.PersonalInfoService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/personal-info")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class PersonalInfoController {

    private final PersonalInfoService personalInfoService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadPersonalInfo(@RequestParam("file") MultipartFile file) {
            this.personalInfoService.seedPersonalInfo(file);
            return new ResponseEntity<>("File uploaded and database seeded successfully", HttpStatus.OK);
    }

}

