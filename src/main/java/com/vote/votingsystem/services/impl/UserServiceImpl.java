package com.vote.votingsystem.services.impl;

import com.vote.votingsystem.handler.exceptions.UserAlreadyVotedException;
import com.vote.votingsystem.handler.exceptions.UserNotFoundException;
import com.vote.votingsystem.models.dtos.UserDTO;
import com.vote.votingsystem.models.entities.User;
import com.vote.votingsystem.models.entities.Vote;
import com.vote.votingsystem.models.entities.VoteOption;
import com.vote.votingsystem.repositories.UserRepository;
import com.vote.votingsystem.repositories.VoteOptionRepository;
import com.vote.votingsystem.repositories.VoteRepository;
import com.vote.votingsystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final VoteOptionRepository voteOptionRepository;

    private final VoteRepository voteRepository;

    private final ModelMapper modelMapper;

    @Override
    public void vote(Long userId, Long voteOptionId) {

        if (this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("No user with this id found")).isHasVoted()) {
            throw new UserAlreadyVotedException("User has already voted");
        }

        VoteOption voteOption = this.voteOptionRepository.findById(voteOptionId).orElseThrow();

        voteOption.setCount(voteOption.getCount() + 1);

        Vote vote  = voteOption.getVote();
        vote.setCount(vote.getCount() + 1);

        User user = this.userRepository.findById(userId).get();
        user.setHasVoted(true);

        this.userRepository.save(user);
        this.voteOptionRepository.save(voteOption);
        this.voteRepository.save(vote);
    }

    @Override
    public UserDTO getUserDetails(Long userId) {

        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("No user with this id found"));

        return this.modelMapper.map(user, UserDTO.class);
    }
}
