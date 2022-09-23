package com.trabalho;

public class IllegalNumberException extends RuntimeException {
    public IllegalNumberException(){
        super("Numero inválido");
    }
}