package unmsm.BibliotecaCentralPedroZulen.exception;

import java.io.Serial;
import java.io.Serializable;

public class UserException extends RuntimeException implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public UserException(String mensaje){
        super(mensaje);
    }

}
