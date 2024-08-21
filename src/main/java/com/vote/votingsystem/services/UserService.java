package com.vote.votingsystem.services;

import com.vote.votingsystem.models.dtos.UserDTO;

public interface UserService {

    void vote(Long userId, Long voteOptionId);

    UserDTO getUserDetails(Long userId);
}
