package com.ticket.ticket.utils;

public class CustomExceptions {
    public static class EventoSoldOutException extends RuntimeException {
        public EventoSoldOutException(String message) {
            super(message);
        }
    }
    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    public static class CapacidadInsuficienteException extends RuntimeException {
        public CapacidadInsuficienteException(String message) {
            super(message);
        }
    }

    public static class EventoNoEncontradoException extends RuntimeException {
        public EventoNoEncontradoException(String message) {
            super(message);
        }
    }

}
