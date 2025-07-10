package unmsm.BibliotecaCentralPedroZulen.model;

import lombok.Data;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;


@Entity
@Data
@Table(name = "Prestamo")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrestamo;

    private LocalDate prestamoDate;

    private LocalDate returnDate;

    private String statusPrestamo;

    private boolean deletePrestamo;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;
}


