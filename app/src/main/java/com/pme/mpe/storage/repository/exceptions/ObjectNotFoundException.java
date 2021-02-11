package com.pme.mpe.storage.repository.exceptions;

public class ObjectNotFoundException extends Exception{
    public ObjectNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
