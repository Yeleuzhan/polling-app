package com.example.pollingapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
@Data
public class ChoiceVoteCount {

    private Long choiceId;
    private Long voteCount;

}
