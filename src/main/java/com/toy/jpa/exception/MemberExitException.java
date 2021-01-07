package com.toy.jpa.exception;

public class MemberExitException extends IllegalStateException {
    public MemberExitException(String message) {
        super(message);
    }
}
