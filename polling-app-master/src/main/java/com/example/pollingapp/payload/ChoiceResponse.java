package com.example.pollingapp.payload;

import lombok.Data;

@Data
public class ChoiceResponse {

    private Long id;
    private String text;
    private Long voteCount;

}
