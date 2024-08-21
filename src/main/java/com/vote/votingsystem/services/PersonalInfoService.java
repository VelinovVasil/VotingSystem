package com.vote.votingsystem.services;

import org.springframework.web.multipart.MultipartFile;

public interface PersonalInfoService {

    void seedPersonalInfo(MultipartFile file);
}
