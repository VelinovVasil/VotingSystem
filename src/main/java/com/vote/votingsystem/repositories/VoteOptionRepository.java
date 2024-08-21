package com.vote.votingsystem.repositories;

import com.vote.votingsystem.models.entities.VoteOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteOptionRepository extends JpaRepository<VoteOption, Long> {

}
