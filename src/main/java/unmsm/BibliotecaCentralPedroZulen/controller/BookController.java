package unmsm.BibliotecaCentralPedroZulen.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import unmsm.BibliotecaCentralPedroZulen.Utils.UtilsLibrary;
import unmsm.BibliotecaCentralPedroZulen.constants.LibraryConstants;
import unmsm.BibliotecaCentralPedroZulen.model.Book;
import unmsm.BibliotecaCentralPedroZulen.service.BookService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public ResponseEntity<?> getAllBooks(){
        try {
            Collection<Book> itemsBooks =  bookService.findAllBooks();
            return new ResponseEntity<>(itemsBooks, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return UtilsLibrary.getResponseEntity(LibraryConstants.AN_ERROR_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/user")
    public ResponseEntity<?> getAllBooksUser(){
        try {
            Collection<Book> itemsBooks =  bookService.findAllBooks();
            return new ResponseEntity<>(itemsBooks, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return UtilsLibrary.getResponseEntity(LibraryConstants.AN_ERROR_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/admin/create")
    public ResponseEntity<String> createBook(@RequestBody(required = true) Book book) {
        try {
            return bookService.saveBook(book);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return UtilsLibrary.getResponseEntity(LibraryConstants.AN_ERROR_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PutMapping("/admin/update/{bookId}")
    public ResponseEntity<?> updateBook(@PathVariable Long bookId, @RequestBody Book bookUpdate){
        try{
            return bookService.updateBook(bookId, bookUpdate);
        }catch (Exception e){
            e.printStackTrace();
        }
        return UtilsLibrary.getResponseEntity(LibraryConstants.AN_ERROR_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/admin/delete/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Long bookId){
        try{
            return bookService.deleteBook(bookId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return UtilsLibrary.getResponseEntity(LibraryConstants.AN_ERROR_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/user/category/{categoryId}")
    public Collection<Book> findByCategory(@PathVariable Long categoryId) {
        return bookService.findByCategory(categoryId);
    }
    @GetMapping("user/{bookId}")
    public ResponseEntity<?> getBookById(@PathVariable Long bookId) {
        try {
            return bookService.findBookById(bookId);
        } catch (Exception e) {
            e.printStackTrace();
            return UtilsLibrary.getResponseEntity(LibraryConstants.AN_ERROR_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/search")
    public ResponseEntity<?> searchBooksByAuthorOrName(@RequestParam String searchTerm) {
        try {
            Collection<Book> searchResult = bookService.searchBooksByAuthorOrName(searchTerm);
            return new ResponseEntity<>(searchResult, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return UtilsLibrary.getResponseEntity(LibraryConstants.AN_ERROR_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/user/favoriteBooks/{userId}")
    public ResponseEntity<?> listFavoriteBooksByUserId(@PathVariable Long userId) {
        try {
            Set<Book> favoriteBooks = bookService.listBooksByUserId(userId);
            return new ResponseEntity<>(favoriteBooks, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error al obtener los libros favoritos del usuario.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}