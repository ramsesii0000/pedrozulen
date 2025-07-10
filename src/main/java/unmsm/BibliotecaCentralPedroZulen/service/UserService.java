package unmsm.BibliotecaCentralPedroZulen.service;

import unmsm.BibliotecaCentralPedroZulen.exception.BookException;
import unmsm.BibliotecaCentralPedroZulen.exception.UserException;
import unmsm.BibliotecaCentralPedroZulen.model.Book;
import unmsm.BibliotecaCentralPedroZulen.model.User;
import unmsm.BibliotecaCentralPedroZulen.repository.BookRepository;
import unmsm.BibliotecaCentralPedroZulen.repository.RoleRepository;
import unmsm.BibliotecaCentralPedroZulen.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final BookRepository bookRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public boolean addBookToFavorites(Long userId, Long bookId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if (userOptional.isPresent() && bookOptional.isPresent()) {
            User user = userOptional.get();
            Book book = bookOptional.get();
            if (!user.getFavoriteBooks().contains(book)) {
                user.getFavoriteBooks().add(book);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    public List<Book> getFavoriteBooksByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getFavoriteBooks();
        } else {
            throw new NoSuchElementException("User not found with ID: " + userId);
        }
    }

    @Transactional
    public boolean removeBookFromFavorites(Long userId, Long bookId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if (userOptional.isPresent() && bookOptional.isPresent()) {
            User user = userOptional.get();
            Book book = bookOptional.get();
            if (user.getFavoriteBooks().contains(book)) {
                user.getFavoriteBooks().remove(book);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }


    public User createUser(User newUser) {
        if (userIsExist(newUser.getNameUser(), newUser.getLastnameUser())) {
            throw new UserException("Ya existe un usuario con el mismo nombre y apellido.");
        }

        assignUserRole(newUser);
        newUser.setPasswordUser(passwordEncoder.encode(newUser.getPasswordUser()));
        userRepository.save(newUser);
        return newUser;
    }

    public boolean userIsExist(String nameUser, String lastnameUser) {
        Optional<User> userIsPresent = userRepository.findByNameUserAndLastnameUser(nameUser, lastnameUser);
        return userIsPresent.isPresent();
    }

    private void assignUserRole(User user) {
        try {
            user.setRole(roleRepository.findByNameRol("ROLE_USER"));
        } catch (Exception ex) {
            throw new UserException("Error al asignar rol al usuario");
        }
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }
    public List<User> getUsersByRole(String roleName) {
        return userRepository.findByRole_NameRol(roleName);
    }

}
