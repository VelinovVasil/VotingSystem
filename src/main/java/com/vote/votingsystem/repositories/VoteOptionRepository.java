package com.vote.votingsystem.repositories;

import com.vote.votingsystem.models.entities.VoteOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteOptionRepository extends JpaRepository<VoteOption, Long> {

    List<VoteOption> findAllByVoteId(Long voteId);

}
