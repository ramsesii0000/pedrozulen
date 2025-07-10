package unmsm.BibliotecaCentralPedroZulen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import unmsm.BibliotecaCentralPedroZulen.Utils.UtilsLibrary;
import unmsm.BibliotecaCentralPedroZulen.constants.LibraryConstants;
import unmsm.BibliotecaCentralPedroZulen.model.Book;
import unmsm.BibliotecaCentralPedroZulen.repository.BookRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Collection<Book> findAllBooks() {
        return (Collection<Book>) bookRepository.findAll();
    }

    public Collection<Book> findByCategory(Long idCategory) {
        return (Collection<Book>) bookRepository.findByCategory_IdCategory(idCategory);
    }

    public ResponseEntity<String> saveBook(Book book) {
        if (!validateBookData(book)) {
            return UtilsLibrary.getResponseEntity(LibraryConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        }
        Book existingBook = bookRepository.findByNameBook(book.getNameBook());
        if (existingBook != null) {
            return UtilsLibrary.getResponseEntity("Este libro ya existe", HttpStatus.BAD_REQUEST);
        }
        book.setStatusBook("Disponible");
        bookRepository.save(book);
        return UtilsLibrary.getResponseEntity("El libro fue registrado con éxito", HttpStatus.CREATED);
    }


    public ResponseEntity<?> updateBook(@PathVariable Long bookId, @RequestBody Book bookUpdate) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isEmpty()) {
            return UtilsLibrary.getResponseEntity("No existe el libro con id: " + bookId, HttpStatus.BAD_REQUEST);
        }

        if (!validateBookData(bookUpdate)) {
            return UtilsLibrary.getResponseEntity(LibraryConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        }

        Book existingBook = bookRepository.findByNameBookAndIdBookNot(bookUpdate.getNameBook(), bookId);
        if (existingBook != null) {
            return UtilsLibrary.getResponseEntity("Este libro ya existe", HttpStatus.BAD_REQUEST);
        }

        book.get().setNameBook(bookUpdate.getNameBook());
        book.get().setAuthor(bookUpdate.getAuthor());
        book.get().setDescription(bookUpdate.getDescription());
        book.get().setPublicationDate(bookUpdate.getPublicationDate());
        book.get().setStatusBook(bookUpdate.getStatusBook());
        book.get().setPicture(bookUpdate.getPicture());
        book.get().setCategory(bookUpdate.getCategory());

        bookRepository.save(book.get());
        return UtilsLibrary.getResponseEntity("El libro fue actualizado con éxito", HttpStatus.OK);
    }

    public ResponseEntity<?> deleteBook(@PathVariable Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isEmpty()) {
            return UtilsLibrary.getResponseEntity("No existe el libro con id: " + bookId, HttpStatus.BAD_REQUEST);
        } else {
            bookRepository.deleteById(bookId);
            return UtilsLibrary.getResponseEntity("El libro fue eliminado con éxito", HttpStatus.OK);
        }
    }

    public static boolean validateBookData(Book book) {
        return book.getNameBook() != null && !book.getNameBook().isEmpty() &&
                book.getAuthor() != null && !book.getAuthor().isEmpty() &&
                book.getDescription() != null && !book.getDescription().isEmpty() &&
                book.getPublicationDate() != null &&
                book.getStatusBook() != null && !book.getStatusBook().isEmpty() &&
                book.getPicture() != null && !book.getPicture().isEmpty() &&
                book.getCategory() != null;
    }
    public Collection<Book> searchBooksByAuthorOrName(String searchTerm) {
        List<Book> booksByAuthor = bookRepository.findByAuthorContainingIgnoreCase(searchTerm);
        List<Book> booksByName = bookRepository.findByNameBookContainingIgnoreCase(searchTerm);
        booksByAuthor.addAll(booksByName);
        return booksByAuthor;
    }
    public ResponseEntity<?> findBookById(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            return ResponseEntity.ok(book.get());
        } else {
            return UtilsLibrary.getResponseEntity("No existe el libro con ID: " + bookId, HttpStatus.NOT_FOUND);
        }
    }

    public Set<Book> listBooksByUserId(Long userId) {
        return bookRepository.findBooksByUserId(userId);
    }
}