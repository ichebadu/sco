package com.iche.sco.exception;

public class DrugAlreadyExistException extends RuntimeException{

    public DrugAlreadyExistException(String message){
        super(message);
    }
}
