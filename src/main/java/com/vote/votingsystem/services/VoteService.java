package com.vote.votingsystem.services;

import com.vote.votingsystem.models.dtos.VoteDTO;

import java.io.IOException;
import java.util.List;

public interface VoteService {
    byte[] generateVotingReport(Long voteId) throws IOException;

    VoteDTO getVoteInfo(Long voteId);

    void addVote(VoteDTO dto);

    void deleteVote(Long voteId);

    List<VoteDTO> getAllVotes();

}
