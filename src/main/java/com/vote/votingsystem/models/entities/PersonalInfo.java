package com.vote.votingsystem.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "personal_infos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInfo extends BaseEntity {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name", nullable = false)
    private String middleName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String egn;

    @Column(name = "id_number", nullable = false, unique = true)
    private String idNumber;

    @OneToOne(mappedBy = "personalInfo")
    private User user;
}
