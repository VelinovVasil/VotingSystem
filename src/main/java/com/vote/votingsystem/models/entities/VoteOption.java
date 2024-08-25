package com.vote.votingsystem.models.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vote_options")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteOption extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column
    private int number;

    @Column
    private int count;

    @ManyToOne
    @JoinColumn(name = "vote_id", referencedColumnName = "id")
    @EqualsAndHashCode.Exclude
    private Vote vote;
}
