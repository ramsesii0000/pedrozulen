package unmsm.BibliotecaCentralPedroZulen.repository;

import unmsm.BibliotecaCentralPedroZulen.model.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByCategory_IdCategory(Long idCategory);
    Book findByNameBook(String nameBook);
    Book findByNameBookAndIdBookNot(String nameBook, Long idBook);

    List<Book> findByAuthorContainingIgnoreCase(String author);

    List<Book> findByNameBookContainingIgnoreCase(String nameBook);

    @Query("SELECT b FROM Book b JOIN b.users u WHERE u.idUser = :userId")
    Set<Book> findBooksByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("UPDATE Book b SET b.statusBook = :status WHERE b.idBook = :idBook")
    void updateBookStatus(@Param("status") String status, @Param("idBook") Long idBook);
}