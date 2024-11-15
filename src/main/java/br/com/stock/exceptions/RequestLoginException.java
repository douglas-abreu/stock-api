package br.com.stock.exceptions;

public class RequestLoginException extends RuntimeException {

    public RequestLoginException(String message) {
        super(message);
    }
}
