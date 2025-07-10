package unmsm.BibliotecaCentralPedroZulen.model;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
@Table(name = "Details_prestamo")
public class DetailsPrestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetailsPrestamo;

    @ManyToOne
    @JoinColumn(name = "idBook")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "idPrestamo")
    private Prestamo prestamo;
}
