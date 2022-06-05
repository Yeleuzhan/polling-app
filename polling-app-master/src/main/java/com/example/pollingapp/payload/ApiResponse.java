package com.example.pollingapp.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiResponse {

    private boolean success;
    private String message;

}
