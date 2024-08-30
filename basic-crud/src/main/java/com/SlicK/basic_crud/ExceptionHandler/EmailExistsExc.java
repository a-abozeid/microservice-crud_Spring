package com.SlicK.basic_crud.ExceptionHandler;

public class EmailExistsExc extends RuntimeException{

    public EmailExistsExc(String message){
        super(message);
    }
}
