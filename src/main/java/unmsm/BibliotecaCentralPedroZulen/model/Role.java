package unmsm.BibliotecaCentralPedroZulen.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;


@Entity
@Data
@NoArgsConstructor
@Table(name = "Roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;

    private String nameRol;

    @Override
    public String getAuthority() {
        return nameRol;
    }
}