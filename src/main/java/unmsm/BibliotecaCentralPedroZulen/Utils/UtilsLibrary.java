package unmsm.BibliotecaCentralPedroZulen.Utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UtilsLibrary {

    private UtilsLibrary(){

    }

    public static ResponseEntity<String> getResponseEntity(String message, HttpStatus httpStatus){
        return new ResponseEntity<String>("Mensaje : " + message, httpStatus);
    }

    public class UserException extends RuntimeException {
        public UserException(String message) {
            super(message);
        }
    }

    public class BookException extends RuntimeException {
        public BookException(String message) {
            super(message);
        }
    }

}
