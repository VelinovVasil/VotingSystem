package com.vote.votingsystem.controllers;

import com.vote.votingsystem.models.dtos.VoteDTO;
import com.vote.votingsystem.services.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/voting")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class VoteController {

    private final VoteService voteService;


    @GetMapping("/")
    public ResponseEntity<List<VoteDTO>> getAllVotes() {
        return ResponseEntity.ok(this.voteService.getAllVotes());
    }


    @GetMapping("/download/{voteId}")
    public ResponseEntity<byte[]> downloadVotingReport(@PathVariable Long voteId) throws IOException {
            byte[] report = this.voteService.generateVotingReport(voteId);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=voting_report.xlsx")
                    .body(report);
    }

    @GetMapping("/{voteId}")
    public ResponseEntity<VoteDTO> getVoteInfo(@PathVariable Long voteId) {
        return ResponseEntity.ok(this.voteService.getVoteInfo(voteId));
    }

    @PostMapping ("/")
    public ResponseEntity<String> addVote(@RequestBody VoteDTO dto) {
        this.voteService.addVote(dto);
        return new ResponseEntity<>("Vote added successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{voteId}")
    public ResponseEntity<String> deleteVote(@PathVariable Long voteId) {
        this.voteService.deleteVote(voteId);
        return new ResponseEntity<>("Vote deleted successfully", HttpStatus.OK);
    }
}
