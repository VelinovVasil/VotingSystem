package com.vote.votingsystem.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteDTO {

    private Long id;

    private String name;

    private String description;

    private int count;

    private Set<VoteOptionDTO> voteOptions;


}
