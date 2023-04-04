package com.gamification.collectr.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BadgeNotFoundException extends Exception{
    public BadgeNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
