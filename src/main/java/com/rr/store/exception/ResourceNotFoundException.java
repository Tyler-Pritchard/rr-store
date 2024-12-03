package com.rr.store.exception;

/**
 * Exception thrown when a requested resource is not found.
 * 
 * This exception is typically used for entities such as products, carts,
 * or other domain objects that cannot be located in the database.
 */
public class ResourceNotFoundException extends RuntimeException {

    private final String errorCode;

    /**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     * 
     * @param message the detail message describing the exception
     */
    public ResourceNotFoundException(String message) {
        super(message);
        this.errorCode = "RESOURCE_NOT_FOUND";
    }

    /**
     * Constructs a new ResourceNotFoundException with the specified detail message
     * and an optional error code.
     * 
     * @param message   the detail message describing the exception
     * @param errorCode a custom error code for further classification
     */
    public ResourceNotFoundException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Gets the error code associated with this exception.
     * 
     * @return the error code
     */
    public String getErrorCode() {
        return errorCode;
    }
}
