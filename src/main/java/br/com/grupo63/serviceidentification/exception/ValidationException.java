package br.com.grupo63.serviceidentification.exception;


public class ValidationException extends GenericException {

    public ValidationException(String name, String description) {
        super(name, description);
    }
}
