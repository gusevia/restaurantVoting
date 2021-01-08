package ru.restaurant.util.exception;

public class ExistVoteException extends RuntimeException {
    public ExistVoteException(String message) {
        super(message);
    }
}
