package com.iche.sco.exception;

public class UserDisabledException extends RuntimeException{
    public UserDisabledException(String accountIsDisabled){
        super(accountIsDisabled);
    }
}
