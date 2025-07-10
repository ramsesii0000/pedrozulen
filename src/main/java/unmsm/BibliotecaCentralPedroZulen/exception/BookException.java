package unmsm.BibliotecaCentralPedroZulen.exception;

import java.io.Serial;
import java.io.Serializable;

public class BookException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    public BookException(String message) {
        super(message);
    }
}
