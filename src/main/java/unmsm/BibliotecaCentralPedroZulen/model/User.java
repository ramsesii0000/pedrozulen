package unmsm.BibliotecaCentralPedroZulen.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "User")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    private String nameUser;
    private String lastnameUser;
    private String dniUser;
    private String emailUser;
    private String passwordUser;

    @ManyToOne
    @JoinColumn(name = "idRol")
    private Role role;

    @ManyToMany
    @JoinTable(
            name = "user_favorite_books",
            joinColumns = @JoinColumn(name = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "idBook")
    )
    @JsonIgnore
    private List<Book> favoriteBooks;

    @Transient
    public Long getIdRol() {
        return role != null ? role.getIdRol() : null;
    }
}