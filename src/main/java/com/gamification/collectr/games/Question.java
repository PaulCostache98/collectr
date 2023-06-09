package com.gamification.collectr.games;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Question {

    private String category;
    private String question;
    private String answer;

}
