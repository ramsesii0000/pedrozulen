package unmsm.BibliotecaCentralPedroZulen.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "multa")
public class Multa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMulta;

    private String descriptionMulta;

    private String typeMulta;

    private LocalDate multaDate;

    private String statusMulta;

    private double montoTotal;

    private boolean deleteMulta;

    @ManyToOne
    @JoinColumn(name = "idPrestamo")
    private Prestamo prestamo;
}
