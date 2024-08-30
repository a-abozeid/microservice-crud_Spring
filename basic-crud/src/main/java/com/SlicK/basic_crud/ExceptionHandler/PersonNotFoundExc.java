package com.SlicK.basic_crud.ExceptionHandler;

public class PersonNotFoundExc extends RuntimeException{

    public PersonNotFoundExc(String message){
        super(message);
    }
}
