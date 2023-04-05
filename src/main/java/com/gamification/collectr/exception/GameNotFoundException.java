package com.gamification.collectr.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GameNotFoundException extends Exception{
    public GameNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}