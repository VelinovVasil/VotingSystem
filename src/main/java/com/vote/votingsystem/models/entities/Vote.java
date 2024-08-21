package com.vote.votingsystem.models.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "votes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vote extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private int count;

    @OneToMany(mappedBy = "vote")
    private Set<VoteOption> voteOptions;
}
