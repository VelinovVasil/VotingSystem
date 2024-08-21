package com.vote.votingsystem.controllers;

import com.vote.votingsystem.models.dtos.UserDTO;
import com.vote.votingsystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/vote/{userId}/{voteOptionId}")
    public ResponseEntity<String> vote(@PathVariable Long userId, @PathVariable Long voteOptionId) {
        this.userService.vote(userId, voteOptionId);
        return new ResponseEntity<>("Voted successfully", HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserDetails(@PathVariable Long userId) {
        return ResponseEntity.ok(this.userService.getUserDetails(userId));
    }
}
