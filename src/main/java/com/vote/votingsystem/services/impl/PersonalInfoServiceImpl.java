package com.vote.votingsystem.services.impl;

import com.vote.votingsystem.models.entities.PersonalInfo;
import com.vote.votingsystem.models.entities.User;
import com.vote.votingsystem.repositories.PersonalInfoRepository;
import com.vote.votingsystem.repositories.UserRepository;
import com.vote.votingsystem.services.PersonalInfoService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonalInfoServiceImpl implements PersonalInfoService {

    private final PersonalInfoRepository personalInfoRepository;

    private final UserRepository userRepository;

    @Override
    public void seedPersonalInfo(MultipartFile file) {
        try {
            List<PersonalInfo> personalInfos = parseExcelFile(file.getInputStream());
            this.personalInfoRepository.deleteAll();
            this.personalInfoRepository.saveAll(personalInfos);

            List<User> users = this.userRepository
                                    .findAll()
                                    .stream()
                                    .map(u -> {
                                            u.setHasVoted(false);
                                            return u;
                                    }).toList();

            this.userRepository.saveAll(users);

        } catch (IOException e) {
            throw new RuntimeException("Failed to process Excel file: " + e.getMessage());
        }
    }

    private List<PersonalInfo> parseExcelFile(InputStream is) throws IOException {
        List<PersonalInfo> personalInfos = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }

            String firstName = row.getCell(0).getStringCellValue();
            String middleName = row.getCell(1).getStringCellValue();
            String lastName = row.getCell(2).getStringCellValue();
            String egn = row.getCell(3).getStringCellValue();
            String idNumber = row.getCell(4).getStringCellValue();

            PersonalInfo personalInfo = new PersonalInfo(firstName, middleName, lastName, egn, idNumber, null);
            personalInfos.add(personalInfo);
        }

        workbook.close();
        return personalInfos;
    }
}
