package com.vote.votingsystem.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteOptionDTO {
    private Long id;
    private String name;
    private int number;
    private int count;
}
