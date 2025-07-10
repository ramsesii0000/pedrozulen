package unmsm.BibliotecaCentralPedroZulen.model;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {

    private final Long userId;

    public CustomUserDetails(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getEmailUser(), user.getPasswordUser(), true, true, true, true, authorities);
        this.userId = user.getIdUser();
    }

    public Long getUserId() {
        return userId;
    }
}
