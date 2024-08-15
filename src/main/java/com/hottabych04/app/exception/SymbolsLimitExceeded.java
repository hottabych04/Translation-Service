package com.hottabych04.app.exception;

public class SymbolsLimitExceeded extends RuntimeException{

    public SymbolsLimitExceeded(){
        super("Character limit per hour exceeded");
    }

}
