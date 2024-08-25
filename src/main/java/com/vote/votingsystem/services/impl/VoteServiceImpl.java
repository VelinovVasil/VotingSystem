package com.vote.votingsystem.services.impl;

import com.vote.votingsystem.handler.exceptions.VoteNotFoundException;
import com.vote.votingsystem.models.dtos.VoteDTO;
import com.vote.votingsystem.models.dtos.VoteOptionDTO;
import com.vote.votingsystem.models.entities.Vote;
import com.vote.votingsystem.models.entities.VoteOption;
import com.vote.votingsystem.repositories.VoteOptionRepository;
import com.vote.votingsystem.repositories.VoteRepository;
import com.vote.votingsystem.services.VoteService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;

    private final VoteOptionRepository voteOptionRepository;

    private final ModelMapper modelMapper;

    @Override
    public byte[] generateVotingReport(Long voteId) throws IOException {
        Vote vote = voteRepository.findById(voteId).orElseThrow(() -> new VoteNotFoundException("Vote not found"));

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Voting Report");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Option Name");
        headerRow.createCell(1).setCellValue("Votes Received");
        headerRow.createCell(2).setCellValue("Percentage of Total Votes");

        Set<VoteOption> voteOptions = vote.getVoteOptions();
        int rowIndex = 1;
        for (VoteOption option : voteOptions) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(option.getName());
            row.createCell(1).setCellValue(option.getCount());

            double percentage = (double) option.getCount() / vote.getCount() * 100;
            row.createCell(2).setCellValue(String.format("%.2f%%", percentage));
        }

        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }

    @Override
    public VoteDTO getVoteInfo(Long voteId) {

        Vote vote = this.voteRepository.findById(voteId).orElseThrow();

        VoteDTO dto = this.modelMapper.map(vote, VoteDTO.class);

        Set<VoteOptionDTO> voteOptionDTOs = this.voteOptionRepository.findAllByVoteId(voteId)
                .stream()
                .map(v -> this.modelMapper.map(v, VoteOptionDTO.class))
                .collect(Collectors.toSet());

        dto.setVoteOptions(voteOptionDTOs);

        return dto;
    }

    @Override
    public void addVote(VoteDTO dto) {

        Vote vote = this.modelMapper.map(dto, Vote.class);
        Set<VoteOption> voteOptions = dto.getVoteOptions()
                                        .stream()
                                        .map(v -> {
                                            VoteOption voteOption = this.modelMapper.map(v, VoteOption.class);

                                            voteOption.setVote(vote);

                                            return voteOption;
                                        }).collect(Collectors.toSet());

        vote.setVoteOptions(voteOptions);
        this.voteRepository.save(vote);
        this.voteOptionRepository.saveAll(voteOptions);
    }

    @Override
    public void deleteVote(Long voteId) {

        this.voteOptionRepository.deleteAll(this.voteRepository.findById(voteId).orElseThrow(() -> new VoteNotFoundException("No vote found")).getVoteOptions());
        this.voteRepository.deleteById(voteId);

    }

    @Override
    public List<VoteDTO> getAllVotes() {
        return this.voteRepository
                .findAll()
                .stream()
                .map((element) -> {
                    VoteDTO dto = modelMapper.map(element, VoteDTO.class);
                    Set<VoteOptionDTO> options = this.voteOptionRepository.findAllByVoteId(element.getId())
                            .stream()
                            .map((e) -> modelMapper.map(e, VoteOptionDTO.class))
                            .collect(Collectors.toSet());
                    dto.setVoteOptions(options);
                    return dto;
                }).collect(Collectors.toList());
    }


}
