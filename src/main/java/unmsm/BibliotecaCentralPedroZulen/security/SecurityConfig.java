package unmsm.BibliotecaCentralPedroZulen.security;

import unmsm.BibliotecaCentralPedroZulen.security.filters.JwtAuthenticationFilter;
import unmsm.BibliotecaCentralPedroZulen.security.filters.JwtValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class SecurityConfig {
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authRules -> authRules
                        .requestMatchers(HttpMethod.GET, "/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/category/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/category/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/category/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/book/admin/").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/book/admin/").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/book/admin/").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/category/all/user").permitAll()
                        .requestMatchers(HttpMethod.GET, "/book/user/category/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/book/user").permitAll()
                        .requestMatchers(HttpMethod.GET, "/book/user/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/book/search").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/{userId}/favorites/{bookId}").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/user/{userId}/favorites").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/user/{userId}/favorites/{bookId}").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/prestamo/admin/create").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "prestamo/update/{idPrestamo}/{statusPrestamo}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/prestamo/").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "prestamo/delete/{idPrestamo}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/prestamo/user/{idUser}").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/multa/").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/email/consulta").hasRole("USER")

                        .anyRequest().authenticated())
                .addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationConfiguration.getAuthenticationManager()))
                .csrf(config -> config.disable())
                .sessionManagement(managment -> managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // Elimina "multipart/form-data"

        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }


    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter(){
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(
                new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return bean;
    }
}
