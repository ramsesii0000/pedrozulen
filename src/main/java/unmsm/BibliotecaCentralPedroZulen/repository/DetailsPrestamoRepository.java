package unmsm.BibliotecaCentralPedroZulen.repository;

import unmsm.BibliotecaCentralPedroZulen.model.DetailsPrestamo;
import unmsm.BibliotecaCentralPedroZulen.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailsPrestamoRepository extends JpaRepository<DetailsPrestamo, Long> {
    List<DetailsPrestamo> findByPrestamo_IdPrestamo(Long idPrestamo);
}
