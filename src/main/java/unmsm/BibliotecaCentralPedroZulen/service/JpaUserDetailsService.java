package unmsm.BibliotecaCentralPedroZulen.service;

import unmsm.BibliotecaCentralPedroZulen.model.CustomUserDetails;
import unmsm.BibliotecaCentralPedroZulen.model.Role;
import unmsm.BibliotecaCentralPedroZulen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<unmsm.BibliotecaCentralPedroZulen.model.User> userOptional = userRepository.findByEmailUser(username);

        if(!userOptional.isPresent()) {
            throw new UsernameNotFoundException("Usuario no existe en el sistema!");
        }

        unmsm.BibliotecaCentralPedroZulen.model.User user = userOptional.orElseThrow();

        Role userRole = user.getRole();
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(userRole.getAuthority()));

        return new CustomUserDetails(user, authorities);
    }
}
