package com.example.pollingapp.controller;

import com.example.pollingapp.model.Poll;
import com.example.pollingapp.payload.ApiResponse;
import com.example.pollingapp.payload.PollRequest;
import com.example.pollingapp.payload.PollResponse;
import com.example.pollingapp.payload.VoteRequest;
import com.example.pollingapp.repository.PollRepository;
import com.example.pollingapp.repository.RoleRepository;
import com.example.pollingapp.repository.UserRepository;
import com.example.pollingapp.repository.VoteRepository;
import com.example.pollingapp.security.CurrentUser;
import com.example.pollingapp.security.UserPrincipal;
import com.example.pollingapp.service.PollService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/polls")
public class PollController {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PollService pollService;

    public static final Logger logger = LoggerFactory.getLogger(PollController.class);

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createPoll(@Valid @RequestBody PollRequest pollRequest) {
        Poll poll = pollService.createPoll(pollRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{pollId}")
                .buildAndExpand(poll.getId()).toUri();
        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Poll created Successfully"));
    }

    @GetMapping("/{pollId}")
    private PollResponse getPollById(@CurrentUser UserPrincipal currentUser,
                                     @PathVariable Long pollId) {
        return pollService.getPollById(pollId, currentUser);
    }

    @PostMapping("/{pollId}/votes")
    @PreAuthorize("hasRole('USER')")
    public PollResponse castVote(@CurrentUser UserPrincipal currentUser,
                                 @PathVariable Long pollId,
                                 @Valid @RequestBody VoteRequest voteRequest) {
        return pollService.castVoteAndGetUpdatePoll(pollId, voteRequest, currentUser);
    }

}
