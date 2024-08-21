package com.vote.votingsystem.repositories;


import com.vote.votingsystem.models.entities.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, Long> {

    boolean existsByEgnAndIdNumberAndFirstNameAndMiddleNameAndLastName
            (
            String egn,
            String idNumber,
            String firstName,
            String middleName,
            String lastName
            );

    Optional<PersonalInfo> getPersonalInfoByEgn(String egn);
}
