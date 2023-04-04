package com.gamification.collectr.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class QuestNotFoundException extends Exception{
    public QuestNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
