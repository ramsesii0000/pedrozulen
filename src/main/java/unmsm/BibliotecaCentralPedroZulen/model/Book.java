package unmsm.BibliotecaCentralPedroZulen.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Book")
public class Book implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBook;
    private String nameBook;
    private String author;
    @Column(columnDefinition = "TEXT")
    private String description;
    private LocalDate publicationDate;
    private String statusBook;
    private String picture;

    @ManyToOne
    @JoinColumn(name = "idCategory")
    private Category category;

    @ManyToMany(mappedBy = "favoriteBooks")
    private List<User> users;
}
