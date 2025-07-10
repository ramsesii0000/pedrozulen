package unmsm.BibliotecaCentralPedroZulen.repository;

import unmsm.BibliotecaCentralPedroZulen.model.Book;
import unmsm.BibliotecaCentralPedroZulen.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    List<Prestamo> findByUser_IdUser(Long idUser);

    List<Prestamo> findByDeletePrestamo (Boolean deletePrestamo);
}
