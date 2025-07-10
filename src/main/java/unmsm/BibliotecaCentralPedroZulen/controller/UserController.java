package unmsm.BibliotecaCentralPedroZulen.controller;

import unmsm.BibliotecaCentralPedroZulen.Utils.UtilsLibrary;
import unmsm.BibliotecaCentralPedroZulen.exception.BookException;
import unmsm.BibliotecaCentralPedroZulen.exception.UserException;
import unmsm.BibliotecaCentralPedroZulen.model.Book;
import unmsm.BibliotecaCentralPedroZulen.model.User;
import unmsm.BibliotecaCentralPedroZulen.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> userRegister(@RequestBody User newUser){
        try{
            userService.createUser(newUser);
            return ResponseEntity.ok("Usuario registrado exitosamente");
        }catch (UserException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> listAllUsers(){
        List<User> users = userService.allUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/{userId}/favorites/{bookId}")
    public ResponseEntity<?> addBookToFavorites(@PathVariable Long userId, @PathVariable Long bookId) {
        boolean result = userService.addBookToFavorites(userId, bookId);

        if (result) {
            return ResponseEntity.ok().body("Book added to favorites successfully.");
        } else {
            return ResponseEntity.badRequest().body("Could not add book to favorites.");
        }
    }

    @GetMapping("/{userId}/favorites")
    public ResponseEntity<List<Book>> getFavoriteBooksByUserId(@PathVariable Long userId) {
        List<Book> favoriteBooks = userService.getFavoriteBooksByUserId(userId);
        return ResponseEntity.ok(favoriteBooks);
    }

    @DeleteMapping("/{userId}/favorites/{bookId}")
    public ResponseEntity<?> removeBookFromFavorites(@PathVariable Long userId, @PathVariable Long bookId) {
        boolean result = userService.removeBookFromFavorites(userId, bookId);

        if (result) {
            return ResponseEntity.ok().body("Book removed from favorites successfully.");
        } else {
            return ResponseEntity.badRequest().body("Could not remove book from favorites.");
        }
    }

}

